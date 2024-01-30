package com.prmto.core_domain.usecase

import android.net.Uri
import com.google.common.truth.Truth.assertThat
import com.prmto.core_domain.R
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.UiText
import com.prmto.core_testing.fake_repository.common.MediaAlbumProviderFake
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetImageUrisByFirstAlbumNameUseCaseTest {
    private lateinit var getImageUrisByFirstAlbumNameUseCase: GetImageUrisByFirstAlbumNameUseCase
    private lateinit var mediaAlbumProvider: MediaAlbumProviderFake

    @Before
    fun setUp() {
        mockkStatic(Uri::class)
        mediaAlbumProvider = MediaAlbumProviderFake()
        getImageUrisByFirstAlbumNameUseCase =
            GetImageUrisByFirstAlbumNameUseCase(mediaAlbumProvider)
    }

    @Test
    fun `if album names is empty return error message `() = runTest {
        mediaAlbumProvider.albumNames = emptyList()

        val result = getImageUrisByFirstAlbumNameUseCase()

        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat((result as Resource.Error).uiText).isEqualTo(
            UiText.StringResource(R.string.no_albums_found)
        )
    }

    @Test
    fun `return album names and uris in first album name `() = runTest {
        mediaAlbumProvider.albumNames = listOf("screenshots", "camera")
        every { Uri.parse("content://media/external/images/media/screenshot1") } returns mockk {
            every { lastPathSegment } returns "content://media/external/images/media/screenshot1"
        }
        mediaAlbumProvider.albumsAndUris = mapOf(
            "screenshots" to listOf(
                Uri.parse("content://media/external/images/media/screenshot1"),
            )
        )

        val result = getImageUrisByFirstAlbumNameUseCase()

        assertThat(result).isInstanceOf(Resource.Success::class.java)
        assertThat((result as Resource.Success).data).isEqualTo(
            FirstAlbumNameResult(
                albumNames = listOf("screenshots", "camera"),
                uriInFirstAlbum = listOf(Uri.parse("content://media/external/images/media/screenshot1"))
            )
        )
    }
}