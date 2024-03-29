package com.prmto.gallery.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.navigation.ProfileNestedScreens

internal fun NavGraphBuilder.selectProfileImageGalleryNavigation(
    onPopBacStack: () -> Unit,
    onPopBackStackWithSelectedUri: (selectedPhotoUri: Uri) -> Unit
) {
    composable(ProfileNestedScreens.SelectProfileImageGallery.route) {
        ProfileImageGalleryRoute(
            onPopBacStack = onPopBacStack,
            onPopBackStackWithSelectedUri = onPopBackStackWithSelectedUri
        )
    }
}

internal fun NavController.navigateToProfileImageGallery() {
    navigate(ProfileNestedScreens.SelectProfileImageGallery.route)
}