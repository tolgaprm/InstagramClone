package com.prmto.share_presentation.postPreview.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.share_presentation.navigation.ShareNestedScreens
import com.prmto.share_presentation.postPreview.PostPreviewRoute

internal fun NavGraphBuilder.postPreviewNavigation(
    onPopBackStack: () -> Unit
) {
    composable(
        route = ShareNestedScreens.PostPreviewScreen.route,
        arguments = ShareNestedScreens.PostPreviewScreen.arguments
    ) {
        PostPreviewRoute(
            onPopBackStack = onPopBackStack
        )
    }
}

internal fun NavController.navigateToPostPreview(
    postPreviewPhotoUris: List<Uri>
) {
    navigate(
        ShareNestedScreens.PostPreviewScreen.passArguments(
            postPreviewPhotoUris.map {
                it.toString()
            }.toTypedArray()
        )
    )
}