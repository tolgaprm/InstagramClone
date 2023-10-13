package com.prmto.share_presentation.postCamera.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.share_presentation.navigation.ShareNestedScreens
import com.prmto.share_presentation.postCamera.PostCameraRoute

internal fun NavGraphBuilder.postCameraNavigation() {
    composable(ShareNestedScreens.PostCameraScreen.route) {
        PostCameraRoute()
    }
}

internal fun NavController.navigateToPostCameraScreen() {
    navigate(ShareNestedScreens.PostCameraScreen.route)
}
