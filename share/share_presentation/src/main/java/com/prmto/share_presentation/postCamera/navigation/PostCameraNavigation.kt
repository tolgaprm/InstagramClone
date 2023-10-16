package com.prmto.share_presentation.postCamera.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.share_presentation.navigation.ShareNestedScreens
import com.prmto.share_presentation.postCamera.PostCameraRoute

internal fun NavGraphBuilder.postCameraNavigation(
    onNavigateToHome: () -> Unit,
    onNavigateToPostGallery: () -> Unit
) {
    composable(ShareNestedScreens.PostCameraScreen.route) {
        PostCameraRoute(
            onNavigateToHome = onNavigateToHome,
            onNavigateToPostGallery = onNavigateToPostGallery
        )
    }
}

internal fun NavController.navigateToPostCameraScreen() {
    navigate(ShareNestedScreens.PostCameraScreen.route)
}
