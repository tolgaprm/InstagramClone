package com.prmto.core_domain.usecase

import android.net.Uri
import com.prmto.core_domain.R
import com.prmto.core_domain.common.MediaAlbumProvider
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.UiText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import javax.inject.Inject

class GetImageUrisByFirstAlbumNameUseCase @Inject constructor(
    private val mediaAlbumProvider: MediaAlbumProvider
) {
    suspend operator fun invoke(
        coroutineScope: CoroutineScope? = null
    ): Resource<FirstAlbumNameResult> {
        return coroutineScope?.let {
            val mediaAlbumsDeferred = coroutineScope.async {
                mediaAlbumProvider.getAllAlbumNames()
            }

            val mediaAlbums = mediaAlbumsDeferred.await().toList()
            if (mediaAlbums.isEmpty()) {
                Resource.Error(UiText.StringResource(R.string.no_albums_found))
            } else {
                val urisInAlbum = coroutineScope.async {
                    mediaAlbumProvider.getAllUrisForAlbum(mediaAlbums.first())
                }
                val result = FirstAlbumNameResult(
                    albumNames = mediaAlbums,
                    uriInFirstAlbum = urisInAlbum.await().toList()
                )
                Resource.Success(result)
            }
        } ?: kotlin.run {
            Resource.Error(UiText.unknownError())
        }
    }
}

data class FirstAlbumNameResult(
    val albumNames: List<String>,
    val uriInFirstAlbum: List<Uri>
)