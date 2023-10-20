package com.prmto.share_presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.prmto.core_presentation.navigation.NestedNavigation
import com.prmto.share_presentation.postCamera.navigation.navigateToPostCameraScreen
import com.prmto.share_presentation.postCamera.navigation.postCameraNavigation
import com.prmto.share_presentation.postGallery.navigation.navigateToPostGalleryScreen
import com.prmto.share_presentation.postGallery.navigation.postGalleryNavigation

fun NavGraphBuilder.shareNestedNavigation(
    navController: NavController,
    onNavigateToHome: () -> Unit,
) {
    navigation(
        route = NestedNavigation.Share.route,
        startDestination = ShareNestedScreens.PostGalleryScreen.route
    ) {
        postCameraNavigation(
            onNavigateToHome = onNavigateToHome,
            onNavigateToPostGallery = navController::navigateToPostGalleryScreen
        )

        postGalleryNavigation(
            onNavigateToPostCamera = navController::navigateToPostCameraScreen,
            onPopBackStack = navController::popBackStack
        )
    }
}

fun NavController.navigateToShareNestedNavigation() {
    navigate(NestedNavigation.Share.route)
}