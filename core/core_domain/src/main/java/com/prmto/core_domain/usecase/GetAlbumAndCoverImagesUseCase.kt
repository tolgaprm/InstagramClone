package com.prmto.core_domain.usecase

import android.net.Uri
import com.prmto.core_domain.repository.mediafile.MediaAlbumProvider
import javax.inject.Inject

class GetAlbumAndCoverImagesUseCase @Inject constructor(
    private val mediaAlbumProvider: MediaAlbumProvider
) {
    suspend operator fun invoke(
        albumNames: List<String>
    ): List<AlbumAndCoverImage> {
        val albumAndCoverImage = mutableListOf<AlbumAndCoverImage>()
        albumNames.forEach { albumName ->
            val firstImageUri = mediaAlbumProvider.getFirstImageUriOfTheAlbum(albumName)
            albumAndCoverImage.add(
                AlbumAndCoverImage(
                    albumName = albumName,
                    firstImageUri = firstImageUri
                )
            )
        }
        return albumAndCoverImage
    }
}

data class AlbumAndCoverImage(
    val albumName: String,
    val firstImageUri: Uri
)