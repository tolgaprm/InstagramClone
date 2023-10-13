package com.prmto.camera.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashAuto
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.prmto.camera.R
import com.prmto.camera.util.CameraFlashMode

@Composable
fun CameraFlashModeButton(
    modifier: Modifier = Modifier,
    isVisibleCameraFlashMode: Boolean = true,
    cameraFlashMode: CameraFlashMode,
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
                imageVector = newFlashIcon, contentDescription = newFlashContentDescription
            )
        }
    }
}