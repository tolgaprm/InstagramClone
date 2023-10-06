package com.prmto.gallery.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.navigation.ProfileNestedScreens

fun NavGraphBuilder.selectProfileImageGalleryNavigation(onPopBacStack: () -> Unit) {
    composable(ProfileNestedScreens.SelectProfileImageGallery.route) {
        ProfileImageGalleryRoute(
            onPopBacStack = onPopBacStack
        )
    }
}

fun NavController.navigateToProfileImageGallery() {
    navigate(ProfileNestedScreens.SelectProfileImageGallery.route)
}