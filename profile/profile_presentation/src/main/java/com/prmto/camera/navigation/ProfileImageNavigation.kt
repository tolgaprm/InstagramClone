package com.prmto.camera.navigation

import androidx.camera.core.CameraSelector
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.camera.ProfileCameraScreen
import com.prmto.camera.ProfileCameraScreenEvent
import com.prmto.camera.ProfileImageViewModel
import com.prmto.camera.rememberCameraControllerWithImageCapture
import com.prmto.navigation.ProfileScreen

fun NavGraphBuilder.profileCameraNavigation(
    onPopBacStack: () -> Unit
) {
    composable(ProfileScreen.CameraForProfileImage.route) {
        val viewModel: ProfileImageViewModel = hiltViewModel()
        val profileCameraUiState = viewModel.uiState.collectAsStateWithLifecycle()
        val cameraController = rememberCameraControllerWithImageCapture()
        ProfileCameraScreen(
            profileCameraUiState = profileCameraUiState.value,
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
            onPopBackStack = onPopBacStack,
            onEvent = viewModel::onEvent
        )

        LaunchedEffect(
            key1 = profileCameraUiState.value.cameraFlashMode,
            block = {
                cameraController.setFlashMode(profileCameraUiState.value.getFlashMode())
            }
        )
    }
}