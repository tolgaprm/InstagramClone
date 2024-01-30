package com.prmto.core_domain.usecase

import android.net.Uri
import androidx.core.net.toUri
import com.google.common.truth.Truth.assertThat
import com.prmto.core_testing.fake_repository.common.MediaAlbumProviderFake
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetAlbumAndCoverImagesUseCaseTest {
    private lateinit var getAlbumAndCoverImagesUseCase: GetAlbumAndCoverImagesUseCase
    private lateinit var mediaAlbumProvider: MediaAlbumProviderFake

    @Before
    fun setUp() {
        mockkStatic(Uri::class)
        mediaAlbumProvider = MediaAlbumProviderFake()
        getAlbumAndCoverImagesUseCase = GetAlbumAndCoverImagesUseCase(mediaAlbumProvider)
    }

    @Test
    fun `should return empty list when there is no album`() = runTest {
        mediaAlbumProvider.albumNames = emptyList()

        val albumAndCoverImage = getAlbumAndCoverImagesUseCase(mediaAlbumProvider.albumNames)

        assertThat(albumAndCoverImage).isEmpty()
    }

    @Test
    fun `should return album and coverImageUri given album names`() = runTest {
        mediaAlbumProvider.albumNames = listOf("screenshots", "camera")
        every { Uri.parse("content://media/external/images/media/screenshot1") } returns mockk {
            every { lastPathSegment } returns "content://media/external/images/media/screenshot1"
        }
        every { Uri.parse("content://media/external/images/media/camera1") } returns mockk {
            every { lastPathSegment } returns "content://media/external/images/media/camera1"
        }



        mediaAlbumProvider.albumsAndUris = mapOf(
            "screenshots" to listOf(
                Uri.parse("content://media/external/images/media/screenshot1"),
            ),
            "camera" to listOf(
                Uri.parse("content://media/external/images/media/camera1"),
            )
        )

        val albumAndCoverImage = getAlbumAndCoverImagesUseCase(mediaAlbumProvider.albumNames)

        assertThat(albumAndCoverImage).hasSize(2)
        assertThat(albumAndCoverImage[0].albumName).isEqualTo("screenshots")
        assertThat(albumAndCoverImage[0].firstImageUri).isEqualTo("content://media/external/images/media/screenshot1".toUri())

        assertThat(albumAndCoverImage[1].albumName).isEqualTo("camera")
        assertThat(albumAndCoverImage[1].firstImageUri).isEqualTo("content://media/external/images/media/camera1".toUri())
    }
}