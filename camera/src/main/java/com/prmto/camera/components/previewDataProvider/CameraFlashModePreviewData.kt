package com.prmto.camera.components.previewDataProvider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.prmto.camera.util.CameraFlashMode

class CameraFlashModePreviewData : PreviewParameterProvider<CameraFlashMode> {
    override val values: Sequence<CameraFlashMode>
        get() = sequenceOf(
            CameraFlashMode.AUTO,
            CameraFlashMode.ON,
            CameraFlashMode.OFF
        )
}