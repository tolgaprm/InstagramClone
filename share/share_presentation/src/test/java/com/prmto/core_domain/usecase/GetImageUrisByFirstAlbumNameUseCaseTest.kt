package com.prmto.core_domain.usecase

import android.net.Uri
import com.google.common.truth.Truth.assertThat
import com.prmto.core_domain.R
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.UiText
import com.prmto.core_testing.fake_repository.common.MediaAlbumProviderFake
import com.prmto.core_testing.util.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetImageUrisByFirstAlbumNameUseCaseTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var mediaAlbumProvider: MediaAlbumProviderFake
    private lateinit var getImageUrisByFirstAlbumNameUseCase: GetImageUrisByFirstAlbumNameUseCase

    @Before
    fun setUp() {
        mockkStatic(Uri::class)
        mediaAlbumProvider = MediaAlbumProviderFake()
        getImageUrisByFirstAlbumNameUseCase =
            GetImageUrisByFirstAlbumNameUseCase(mediaAlbumProvider)
        every { Uri.parse("uri1") } returns mockk {
            every { lastPathSegment } returns "uri1"
        }
        every { Uri.parse("uri2") } returns mockk {
            every { lastPathSegment } returns "uri2"
        }
        every { Uri.parse("uri3") } returns mockk {
            every { lastPathSegment } returns "uri3"
        }
        every { Uri.parse("uri4") } returns mockk {
            every { lastPathSegment } returns "uri4"
        }
    }

    @Test
    fun `when getImageUrisByFirstAlbumNameUseCase invoked with empty albumNames then return error`() =
        runTest {
            mediaAlbumProvider.albumNames = emptyList()

            val result = getImageUrisByFirstAlbumNameUseCase()

            assertThat(result).isInstanceOf(Resource.Error::class.java)
            val message = (result as Resource.Error).uiText
            assertThat(message).isEqualTo(UiText.StringResource(R.string.no_albums_found))
        }

    @Test
    fun `getImageUrisByFirstAlbumNameUseCase invoked with non empty albumNames then return success`() =
        runTest {
            mediaAlbumProvider.albumNames = listOf("album1", "album2")
            mediaAlbumProvider.albumsAndUris = mapOf(
                "album1" to listOf(Uri.parse("uri1"), Uri.parse("uri2")),
                "album2" to listOf((Uri.parse("uri3")), Uri.parse("uri4"))
            )

            val result = getImageUrisByFirstAlbumNameUseCase()

            assertThat(result).isInstanceOf(Resource.Success::class.java)
            val firstAlbumNameResult = (result as Resource.Success).data
            assertThat(firstAlbumNameResult.albumNames).isEqualTo(listOf("album1", "album2"))
            assertThat(firstAlbumNameResult.uriInFirstAlbum).isEqualTo(
                listOf(
                    Uri.parse("uri1"),
                    Uri.parse("uri2")
                )
            )
        }
}