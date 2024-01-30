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
        return albumNames.map { albumName ->
            val firstImageUri = mediaAlbumProvider.getFirstImageUriOfTheAlbum(albumName)
            AlbumAndCoverImage(
                albumName = albumName,
                firstImageUri = firstImageUri
            )
        }
    }
}

data class AlbumAndCoverImage(
    val albumName: String,
    val firstImageUri: Uri
)