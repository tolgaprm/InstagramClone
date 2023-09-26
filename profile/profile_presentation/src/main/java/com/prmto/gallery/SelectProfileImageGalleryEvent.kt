package com.prmto.gallery

import android.net.Uri

sealed interface SelectProfileImageGalleryEvent {
    data class SelectAlbum(val albumName: String) : SelectProfileImageGalleryEvent

    data class SelectImage(val uri: Uri) : SelectProfileImageGalleryEvent
}