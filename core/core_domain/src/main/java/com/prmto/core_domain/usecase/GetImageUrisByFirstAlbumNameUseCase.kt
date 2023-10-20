package com.prmto.core_domain.usecase

import android.net.Uri
import com.prmto.core_domain.R
import com.prmto.core_domain.common.MediaAlbumProvider
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.UiText
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetImageUrisByFirstAlbumNameUseCase @Inject constructor(
    private val mediaAlbumProvider: MediaAlbumProvider
) {
    suspend operator fun invoke(): Resource<FirstAlbumNameResult> {
        return coroutineScope {
            val mediaAlbumsDeferred = async {
                mediaAlbumProvider.getAllAlbumNames()
            }

            val mediaAlbums = mediaAlbumsDeferred.await().toList()
            if (mediaAlbums.isEmpty()) {
                Resource.Error(UiText.StringResource(R.string.no_albums_found))
            } else {
                val urisInAlbum = async {
                    mediaAlbumProvider.getAllUrisForAlbum(mediaAlbums.first())
                }
                val result = FirstAlbumNameResult(
                    albumNames = mediaAlbums,
                    uriInFirstAlbum = urisInAlbum.await().toList()
                )
                Resource.Success(result)
            }
        }

    }
}

data class FirstAlbumNameResult(
    val albumNames: List<String>,
    val uriInFirstAlbum: List<Uri>
)