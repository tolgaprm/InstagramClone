package com.prmto.share_presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.prmto.core_presentation.navigation.NestedNavigation
import com.prmto.share_presentation.postCamera.navigation.postCameraNavigation

fun NavGraphBuilder.shareNestedNavigation(
    navController: NavController,
    onNavigateToHome: () -> Unit,
) {
    navigation(
        route = NestedNavigation.Share.route,
        startDestination = ShareNestedScreens.PostCameraScreen.route
    ) {
        postCameraNavigation(
            onNavigateToHome = onNavigateToHome,
            onNavigateToPostGallery = {}//navController::navigateToPostGallery
        )
    }
}

fun NavController.navigateToShareNestedNavigation() {
    navigate(NestedNavigation.Share.route)
}