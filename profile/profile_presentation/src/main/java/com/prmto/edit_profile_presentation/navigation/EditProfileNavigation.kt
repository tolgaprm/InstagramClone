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
    composable(ProfileNestedScreens.EditProfile.route) { entry ->
        val selectedNewProfileUriString = entry.savedStateHandle.get<String>("selectedPhotoUri")
        EditProfileRoute(
            onPopBackStack = onPopBackStack,
            onNavigateToProfileCamera = onNavigateToProfileCamera,
            onNavigateToGallery = onNavigateToGallery,
            selectedNewProfileImage = selectedNewProfileUriString
        )
    }
}

fun NavController.navigateToEditProfile() {
    navigate(ProfileNestedScreens.EditProfile.route)
}