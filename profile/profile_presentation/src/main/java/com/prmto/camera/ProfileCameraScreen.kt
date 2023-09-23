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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FlipCameraAndroid
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.prmto.core_presentation.components.ShowPermissionPermanentlyDeclinedScreen
import com.prmto.core_presentation.components.ShowRationaleMessageForPermission
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme
import com.prmto.core_presentation.util.HandlePermissionStatus
import com.prmto.profile_presentation.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ProfileCameraScreen(
    modifier: Modifier = Modifier,
    onChangeCamera: () -> Unit,
    onStartCamera: (PreviewView) -> Unit,
    onTakePhoto: () -> Unit,
    onPopBackStack: () -> Unit
) {
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect(key1 = Unit) {
        if (!permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { ProfileImageTopBar(onPopBackStack = onPopBackStack) }
    ) { paddingValues ->
        HandlePermissionStatus(
            permissionStatus = permissionState.status,
            onPermissionGranted = {
                ProfileImageCameraContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    onStartCamera = onStartCamera,
                    onChangeCamera = onChangeCamera,
                    onTakePhoto = onTakePhoto
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
    onStartCamera: (PreviewView) -> Unit,
    onChangeCamera: () -> Unit,
    onTakePhoto: () -> Unit
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
                onStartCamera = onStartCamera,
                onChangeCamera = onChangeCamera
            )
            ScreenBottomSection(
                halfHeightOfTheParent = halfHeightOfTheParent,
                onTakePhoto = onTakePhoto
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ProfileImageTopBar(
    onPopBackStack: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.photo)) },
        navigationIcon = {
            IconButton(onClick = { onPopBackStack() }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close)
                )
            }
        },
    )
}

@Composable
private fun InstaCameraSection(
    modifier: Modifier = Modifier,
    halfHeightOfTheParent: Dp,
    onStartCamera: (PreviewView) -> Unit = {},
    onChangeCamera: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(halfHeightOfTheParent)
    ) {
        InstaCamera(onStartCamera = onStartCamera)
        IconButton(
            onClick = { onChangeCamera() },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.FlipCameraAndroid,
                contentDescription = stringResource(R.string.flip_camera)
            )
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
            onPopBackStack = {}
        )
    }
}