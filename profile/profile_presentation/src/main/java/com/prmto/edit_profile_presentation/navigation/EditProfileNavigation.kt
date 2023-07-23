package com.prmto.edit_profile_presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.edit_profile_presentation.EditProfileScreen
import com.prmto.navigation.ProfileScreen

fun NavGraphBuilder.editProfileNavigation(
    onPopBackStack: () -> Unit
) {
    composable(ProfileScreen.EditProfile.route) {
        EditProfileScreen(
            onPopBackStack = onPopBackStack
        )
    }
}