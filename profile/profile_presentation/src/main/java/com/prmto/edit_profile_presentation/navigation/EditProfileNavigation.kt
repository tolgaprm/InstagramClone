package com.prmto.edit_profile_presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.edit_profile_presentation.EditProfileRoute
import com.prmto.navigation.ProfileNestedScreens

fun NavGraphBuilder.editProfileNavigation(
    onPopBackStack: () -> Unit,
    onNavigateToProfileCamera: () -> Unit,
    onNavigateToGallery: () -> Unit,
) {
    composable(ProfileNestedScreens.EditProfile.route) {
        EditProfileRoute(
            onPopBackStack = onPopBackStack,
            onNavigateToProfileCamera = onNavigateToProfileCamera,
            onNavigateToGallery = onNavigateToGallery
        )
    }
}

fun NavController.navigateToEditProfile() {
    navigate(ProfileNestedScreens.EditProfile.route)
}