package com.prmto.share_presentation.postCamera

import android.net.Uri

sealed interface PostCameraEvent {
    data object OnClickCameraFlashMode : PostCameraEvent

    data class OnCroppedImage(val uri: Uri) : PostCameraEvent
}