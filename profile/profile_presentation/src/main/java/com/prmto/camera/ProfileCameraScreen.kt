package com.prmto.camera

import android.Manifest
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.FlashAuto
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.FlipCameraAndroid
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.prmto.common.components.ProfileTopBar
import com.prmto.core_presentation.components.ShowPermissionPermanentlyDeclinedScreen
import com.prmto.core_presentation.components.ShowRationaleMessageForPermission
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme
import com.prmto.core_presentation.util.HandlePermissionStatus
import com.prmto.profile_presentation.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ProfileCameraScreen(
    profileCameraUiState: ProfileImageUiState,
    modifier: Modifier = Modifier,
    onChangeCamera: () -> Unit,
    onStartCamera: (PreviewView) -> Unit,
    onTakePhoto: () -> Unit,
    onPopBackStack: () -> Unit,
    onEvent: (ProfileCameraScreenEvent) -> Unit
) {
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect(key1 = Unit) {
        if (!permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ProfileTopBar(
                titleText = stringResource(R.string.photo),
                onPopBackStack = onPopBackStack
            )
        }
    ) { paddingValues ->
        HandlePermissionStatus(
            permissionStatus = permissionState.status,
            onPermissionGranted = {
                ProfileImageCameraContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    isVisibleCameraFlashMode = profileCameraUiState.isVisibleCameraFlashMode,
                    onStartCamera = onStartCamera,
                    onChangeCamera = onChangeCamera,
                    onTakePhoto = onTakePhoto,
                    cameraFlashMode = profileCameraUiState.cameraFlashMode,
                    onClickFlashMode = {
                        onEvent(ProfileCameraScreenEvent.ClickedFlashMode)
                    }
                )
            },
            onShowRationaleMessage = {
                ShowRationaleMessageForPermission(
                    title = stringResource(R.string.camera_permission_rationale_title),
                    message = stringResource(R.string.camera_permission_rationale_message),
                    launchPermissionAgain = {
                        permissionState.launchPermissionRequest()
                    },
                    icon = Icons.Default.PhotoCamera
                )
            },
            onPermissionDeniedPermanently = {
                ShowPermissionPermanentlyDeclinedScreen(
                    permissionName = stringResource(R.string.camera),
                    imageVector = Icons.Default.PhotoCamera
                )
            }
        )
    }
}

@Composable
fun ProfileImageCameraContent(
    modifier: Modifier = Modifier,
    cameraFlashMode: CameraFlashMode,
    isVisibleCameraFlashMode: Boolean,
    onStartCamera: (PreviewView) -> Unit,
    onChangeCamera: () -> Unit,
    onTakePhoto: () -> Unit,
    onClickFlashMode: () -> Unit
) {
    var halfHeightOfTheParent by remember { mutableStateOf(0.dp) }

    BoxWithConstraints(
        modifier = modifier
    ) {
        halfHeightOfTheParent = maxHeight / 2
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            InstaCameraSection(
                halfHeightOfTheParent = halfHeightOfTheParent,
                isVisibleCameraFlashMode = isVisibleCameraFlashMode,
                onStartCamera = onStartCamera,
                onChangeCamera = onChangeCamera,
                cameraFlashMode = cameraFlashMode,
                onClickFlashMode = onClickFlashMode
            )
            ScreenBottomSection(
                halfHeightOfTheParent = halfHeightOfTheParent,
                onTakePhoto = onTakePhoto
            )
        }
    }
}

@Composable
private fun InstaCameraSection(
    modifier: Modifier = Modifier,
    halfHeightOfTheParent: Dp,
    isVisibleCameraFlashMode: Boolean,
    cameraFlashMode: CameraFlashMode,
    onStartCamera: (PreviewView) -> Unit,
    onChangeCamera: () -> Unit,
    onClickFlashMode: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(halfHeightOfTheParent)
    ) {
        InstaCamera(onStartCamera = onStartCamera)
        IconButton(
            onClick = onChangeCamera,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.FlipCameraAndroid,
                contentDescription = stringResource(R.string.flip_camera)
            )
        }

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
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)

            ) {
                Icon(
                    imageVector = newFlashIcon,
                    contentDescription = newFlashContentDescription
                )
            }
        }
    }
}

@Composable
fun ScreenBottomSection(
    modifier: Modifier = Modifier,
    halfHeightOfTheParent: Dp,
    onTakePhoto: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .height(halfHeightOfTheParent)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
    ) {
        IconButton(
            onClick = onTakePhoto,
            modifier = Modifier
                .align(Alignment.Center)
                .size(100.dp)
                .padding(8.dp)
                .border(2.dp, MaterialTheme.colorScheme.onBackground, CircleShape)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onBackground.copy(0.8f))
        ) {
            Icon(
                modifier = Modifier.size(50.dp),
                imageVector = Icons.Default.Circle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview
@Composable
fun ProfileCameraScreenPreview() {
    InstagramCloneTheme {
        ProfileCameraScreen(
            onChangeCamera = {},
            onStartCamera = {},
            onTakePhoto = {},
            onPopBackStack = {},
            onEvent = {},
            profileCameraUiState = ProfileImageUiState(
                captureUri = null,
                cameraFlashMode = CameraFlashMode.OFF
            )
        )
    }
}