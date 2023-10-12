package com.prmto.camera.navigation

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.camera.core.CameraSelector
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.prmto.camera.ProfileCameraScreen
import com.prmto.camera.ProfileCameraScreenEvent
import com.prmto.camera.ProfileImageViewModel
import com.prmto.camera.crop.CropActivityResultContract
import com.prmto.camera.rememberCameraControllerWithImageCapture

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun ProfileCameraRoute(
    modifier: Modifier = Modifier,
    viewModel: ProfileImageViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit,
    onPopBackStackWithSelectedUri: (selectedPhotoUri: Uri) -> Unit
) {
    val profileCameraUiState = viewModel.uiState.collectAsStateWithLifecycle()
    val cameraController = rememberCameraControllerWithImageCapture()
    val permissionsToRequest = mutableListOf<String>().apply {
        add(Manifest.permission.CAMERA)
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val permissionState =
        rememberMultiplePermissionsState(permissions = permissionsToRequest) { permissionsResult ->
            permissionsToRequest.forEach { permission ->
                viewModel.onEvent(
                    ProfileCameraScreenEvent.PermissionResult(
                        permission = permission,
                        isGranted = permissionsResult[permission] == true,
                    )
                )
            }
        }

    val cropActivityLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(isCircle = true)
    ) { croppedUri ->
        croppedUri?.let {
            viewModel.onEvent(ProfileCameraScreenEvent.PhotoCropped(croppedUri))
            onPopBackStackWithSelectedUri(croppedUri)
        }
    }

    ProfileCameraScreen(
        modifier = modifier,
        profileCameraUiState = profileCameraUiState.value,
        allPermissionsGranted = permissionState.allPermissionsGranted,
        permissionStates = permissionState.permissions,
        dialogQueue = viewModel.visiblePermissionDialogQueue,
        onChangeCamera = {
            cameraController.changeCamera()
            val isFrontCamera =
                cameraController.cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA
            viewModel.onEvent(ProfileCameraScreenEvent.ChangeCameraSelector(isFrontCamera))
        },
        onStartCamera = {
            cameraController.startCamera(it)
        },
        onTakePhoto = {
            cameraController.takePhoto(
                onPhotoCaptured = {
                    viewModel.onEvent(ProfileCameraScreenEvent.PhotoTaken(it))
                }
            )
        },
        onPopBackStack = onPopBackStack,
        onEvent = viewModel::onEvent
    )

    LaunchedEffect(
        key1 = profileCameraUiState.value.cameraFlashMode,
        block = {
            cameraController.setFlashMode(profileCameraUiState.value.getFlashMode())
        }
    )

    LaunchedEffect(key1 = Unit) {
        if (!permissionState.allPermissionsGranted) {
            permissionState.launchMultiplePermissionRequest()
        }
    }

    LaunchedEffect(
        key1 = uiState.value.captureUri
    ) {
        uiState.value.captureUri?.let {
            cropActivityLauncher.launch(it)
        }
    }
}