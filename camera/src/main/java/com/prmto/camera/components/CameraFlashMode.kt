package com.prmto.camera.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashAuto
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.prmto.camera.R
import com.prmto.camera.components.previewDataProvider.CameraFlashModePreviewData
import com.prmto.camera.util.CameraFlashMode

@Composable
fun CameraFlashModeButton(
    modifier: Modifier = Modifier,
    isVisibleCameraFlashMode: Boolean = true,
    tint: Color = MaterialTheme.colorScheme.onBackground,
    cameraFlashMode: CameraFlashMode = CameraFlashMode.OFF,
    onClickFlashMode: () -> Unit
) {
    if (isVisibleCameraFlashMode) {
        val newFlashIcon = when (cameraFlashMode) {
            CameraFlashMode.OFF -> Icons.Default.FlashOff
            CameraFlashMode.ON -> Icons.Default.FlashOn
            CameraFlashMode.AUTO -> Icons.Default.FlashAuto
        }

        val newFlashContentDescription = when (cameraFlashMode) {
            CameraFlashMode.OFF -> stringResource(R.string.flash_off)
            CameraFlashMode.ON -> stringResource(R.string.flash_on)
            CameraFlashMode.AUTO -> stringResource(R.string.flash_auto)
        }

        IconButton(
            onClick = onClickFlashMode,
            modifier = modifier
        ) {
            Icon(
                imageVector = newFlashIcon,
                contentDescription = newFlashContentDescription,
                tint = tint
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CameraFlashModeButtonPreview(
    @PreviewParameter(CameraFlashModePreviewData::class) cameraFlashMode: CameraFlashMode
) {
    CameraFlashModeButton(
        cameraFlashMode = cameraFlashMode,
        onClickFlashMode = {}
    )
}