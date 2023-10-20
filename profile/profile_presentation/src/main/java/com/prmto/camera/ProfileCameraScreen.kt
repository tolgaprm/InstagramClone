package com.prmto.camera

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
import androidx.compose.material.icons.filled.FlipCameraAndroid
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.prmto.camera.components.CameraFlashModeButton
import com.prmto.camera.util.CameraFlashMode
import com.prmto.common.components.ProfileTopBar
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme
import com.prmto.permission.provider.getPermissionInfoProvider
import com.prmto.permission.util.HandleMultiplePermissionStatus
import com.prmto.profile_presentation.R
import com.prmto.camera.R as CameraR

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun ProfileCameraScreen(
    profileCameraUiState: ProfileCameraUiState,
    modifier: Modifier = Modifier,
    allPermissionsGranted: Boolean = false,
    permissionStates: List<PermissionState>,
    dialogQueue: List<String>,
    onChangeCamera: () -> Unit,
    onStartCamera: (PreviewView) -> Unit,
    onTakePhoto: () -> Unit,
    onPopBackStack: () -> Unit,
    onEvent: (ProfileCameraScreenEvent) -> Unit
) {
    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            ProfileTopBar(
                titleText = stringResource(R.string.photo),
                onPopBackStack = onPopBackStack
            )
        }
    ) { paddingValues ->
        if (allPermissionsGranted) {
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
        } else {
            dialogQueue.reversed().forEach { permission ->
                HandleDialogQueue(
                    permission = permission,
                    permissionStates = permissionStates,
                    onDismiss = { onEvent(ProfileCameraScreenEvent.DismissDialog) },
                    onOkClick = {
                        onEvent(ProfileCameraScreenEvent.DismissDialog)
                        permissionStates.find { it.permission == permission }
                            ?.launchPermissionRequest()
                    }
                )
            }
        }
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
                halfHeightOfTheParent = halfHeightOfTheParent, onTakePhoto = onTakePhoto
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
                contentDescription = stringResource(CameraR.string.flip_camera)
            )
        }

        CameraFlashModeButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            isVisibleCameraFlashMode = isVisibleCameraFlashMode,
            cameraFlashMode = cameraFlashMode,
            onClickFlashMode = onClickFlashMode
        )
    }
}

@Composable
fun ScreenBottomSection(
    modifier: Modifier = Modifier, halfHeightOfTheParent: Dp, onTakePhoto: () -> Unit = {}
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HandleDialogQueue(
    permission: String,
    permissionStates: List<PermissionState>,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit
) {
    val permissionInfoProvider = getPermissionInfoProvider(permission)
    val permissionStatus = permissionStates.find { it.permission == permission }?.status ?: return
    HandleMultiplePermissionStatus(
        permissionProvider = permissionInfoProvider,
        onDismiss = onDismiss,
        onOkClick = onOkClick,
        shouldShowRationale = permissionStatus.shouldShowRationale,
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Preview
@Composable
fun ProfileCameraScreenPreview() {
    InstagramCloneTheme {
        ProfileCameraScreen(
            profileCameraUiState =
            ProfileCameraUiState(
                captureUri = null,
                cameraFlashMode = CameraFlashMode.OFF
            ),
            allPermissionsGranted = true,
            permissionStates = listOf(),
            dialogQueue = listOf(),
            onChangeCamera = {},
            onStartCamera = {},
            onTakePhoto = {},
            onPopBackStack = {},
            onEvent = {}
        )
    }
}