package com.prmto.camera.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.navigation.ProfileNestedScreens

fun NavGraphBuilder.profileCameraNavigation(
    onPopBacStack: () -> Unit,
    onPopBackStackWithSelectedUri: (selectedPhotoUri: Uri) -> Unit

) {
    composable(ProfileNestedScreens.ProfileCamera.route) {
        ProfileCameraRoute(
            onPopBackStack = onPopBacStack,
            onPopBackStackWithSelectedUri = onPopBackStackWithSelectedUri
        )
    }
}

fun NavController.navigateToProfileCamera() {
    navigate(ProfileNestedScreens.ProfileCamera.route)
}