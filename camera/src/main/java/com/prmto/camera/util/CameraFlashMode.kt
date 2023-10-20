package com.prmto.camera.util

import androidx.camera.core.ImageCapture

enum class CameraFlashMode(val mode: Int) {
    AUTO(ImageCapture.FLASH_MODE_AUTO),
    ON(ImageCapture.FLASH_MODE_ON),
    OFF(ImageCapture.FLASH_MODE_OFF)
}