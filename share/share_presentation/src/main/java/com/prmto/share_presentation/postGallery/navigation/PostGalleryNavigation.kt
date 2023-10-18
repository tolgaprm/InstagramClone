package com.prmto.share_presentation.postGallery.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.share_presentation.navigation.ShareNestedScreens
import com.prmto.share_presentation.postGallery.PostGalleryRoute

internal fun NavGraphBuilder.postGalleryNavigation(
    onNavigateToPostCamera: () -> Unit,
    onPopBackStack: () -> Unit
) {
    composable(ShareNestedScreens.PostGalleryScreen.route) {
        PostGalleryRoute(
            onNavigateToPostCamera = onNavigateToPostCamera,
            onPopBackStack = onPopBackStack
        )
    }
}

internal fun NavController.navigateToPostGalleryScreen() {
    this.navigate(ShareNestedScreens.PostGalleryScreen.route)
}