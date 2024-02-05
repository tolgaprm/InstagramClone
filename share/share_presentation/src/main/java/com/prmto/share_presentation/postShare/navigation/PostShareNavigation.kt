package com.prmto.share_presentation.postShare.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.share_presentation.navigation.ShareNestedScreens
import com.prmto.share_presentation.postShare.PostShareRoute

internal fun NavGraphBuilder.postShareNavigation(
    onPopBackStack: () -> Unit,
    onNavigateToPostPreview: (List<Uri>) -> Unit
) {
    composable(
        route = ShareNestedScreens.PostShareScreen.route,
        arguments = ShareNestedScreens.PostShareScreen.arguments
    ) {
        PostShareRoute(
            onPopBackStack = onPopBackStack,
            onNavigateToPostPreview = onNavigateToPostPreview
        )
    }
}

internal fun NavController.navigateToPostShareScreen(
) {
    this.navigate(ShareNestedScreens.PostShareScreen.route)
}