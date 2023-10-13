package com.prmto.camera.usecase

import com.prmto.camera.util.CameraFlashMode
import javax.inject.Inject

class GetNewFlashModeUseCase @Inject constructor() {
    operator fun invoke(newFlashMode: CameraFlashMode): CameraFlashMode {
        return when (newFlashMode) {
            CameraFlashMode.OFF -> CameraFlashMode.ON
            CameraFlashMode.ON -> CameraFlashMode.AUTO
            CameraFlashMode.AUTO -> CameraFlashMode.OFF
        }
    }
}