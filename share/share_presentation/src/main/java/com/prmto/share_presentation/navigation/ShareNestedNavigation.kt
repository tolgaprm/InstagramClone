package com.prmto.share_presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.prmto.core_presentation.navigation.NestedNavigation
import com.prmto.share_presentation.postCamera.navigation.navigateToPostCameraScreen
import com.prmto.share_presentation.postCamera.navigation.postCameraNavigation
import com.prmto.share_presentation.postGallery.navigation.navigateToPostGalleryScreen
import com.prmto.share_presentation.postGallery.navigation.postGalleryNavigation
import com.prmto.share_presentation.postPreview.navigation.navigateToPostPreview
import com.prmto.share_presentation.postPreview.navigation.postPreviewNavigation
import com.prmto.share_presentation.postShare.navigation.postShareNavigation

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
            onNavigateToPostGallery = navController::navigateToPostGalleryScreen,
            onNavigateToPostShareScreen = navController::navigate
        )

        postGalleryNavigation(
            onNavigateToPostCamera = navController::navigateToPostCameraScreen,
            onNavigateToPostShare = {
                navController.navigate(route = it)
            },
            onPopBackStack = navController::popBackStack
        )

        postShareNavigation(
            onPopBackStack = navController::popBackStack,
            onNavigateToPostPreview = {
                navController.navigateToPostPreview(it)
            }
        )

        postPreviewNavigation(
            onPopBackStack = navController::popBackStack
        )
    }
}

fun NavController.navigateToShareNestedNavigation() {
    navigate(NestedNavigation.Share.route)
}