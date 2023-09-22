package com.prmto.camera.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.camera.ProfileCameraScreen
import com.prmto.camera.ProfileImageViewModel
import com.prmto.camera.rememberCameraControllerWithImageCapture
import com.prmto.navigation.ProfileScreen

fun NavGraphBuilder.profileCameraNavigation(
    onPopBacStack: () -> Unit
) {
    composable(ProfileScreen.CameraForProfileImage.route) {
        val viewModel: ProfileImageViewModel = hiltViewModel()
        val cameraController = rememberCameraControllerWithImageCapture()
        ProfileCameraScreen(
            onChangeCamera = {
                cameraController.changeCamera()
            },
            onStartCamera = {
                cameraController.startCamera(it)
            },
            onTakePhoto = {
                cameraController.takePhoto(
                    onPhotoCaptured = {
                        viewModel.setCaptureUri(it)
                    }
                )
            },
            onPopBackStack = onPopBacStack
        )
    }
}