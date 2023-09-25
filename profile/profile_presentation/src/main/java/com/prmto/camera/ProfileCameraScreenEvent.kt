package com.prmto.camera

import android.net.Uri
import androidx.camera.core.ImageCapture

sealed interface ProfileCameraScreenEvent {
    data class PhotoTaken(val photoUri: Uri) : ProfileCameraScreenEvent

    data object ClickedFlashMode : ProfileCameraScreenEvent
}

enum class CameraFlashMode(val mode: Int) {
    AUTO(ImageCapture.FLASH_MODE_AUTO),
    ON(ImageCapture.FLASH_MODE_ON),
    OFF(ImageCapture.FLASH_MODE_OFF)
}