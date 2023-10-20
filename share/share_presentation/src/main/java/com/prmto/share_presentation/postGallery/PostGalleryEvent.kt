package com.prmto.share_presentation.postGallery

import android.net.Uri

sealed interface PostGalleryEvent {
    data class OnImageCropped(val uri: Uri) : PostGalleryEvent

    data object OnClickMultipleSelectButton : PostGalleryEvent

    data class OnClickImageItem(val selectedUri: Uri) : PostGalleryEvent

    data object OnOpenBottomSheet : PostGalleryEvent

    data class OnClickAlbumItem(val albumName: String) : PostGalleryEvent
}