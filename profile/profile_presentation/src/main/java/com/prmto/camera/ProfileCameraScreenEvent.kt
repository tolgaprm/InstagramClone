package com.prmto.camera

import android.net.Uri

sealed interface ProfileCameraScreenEvent {
    data class PhotoTaken(val photoUri: Uri) : ProfileCameraScreenEvent

    data class PhotoCropped(val croppedUri: Uri) : ProfileCameraScreenEvent

    data object ClickedFlashMode : ProfileCameraScreenEvent

    data class ChangeCameraSelector(
        val isFrontCamera: Boolean
    ) : ProfileCameraScreenEvent

    data object DismissDialog : ProfileCameraScreenEvent

    data class PermissionResult(
        val permission: String,
        val isGranted: Boolean
    ) : ProfileCameraScreenEvent
}