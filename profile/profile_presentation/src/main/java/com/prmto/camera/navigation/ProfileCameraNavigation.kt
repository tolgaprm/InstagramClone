package com.prmto.camera.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.navigation.ProfileNestedScreens

fun NavGraphBuilder.profileCameraNavigation(
    onPopBacStack: () -> Unit
) {
    composable(ProfileNestedScreens.ProfileCamera.route) {
        ProfileCameraRoute(
            onPopBackStack = onPopBacStack
        )
    }
}

fun NavController.navigateToProfileCamera() {
    navigate(ProfileNestedScreens.ProfileCamera.route)
}