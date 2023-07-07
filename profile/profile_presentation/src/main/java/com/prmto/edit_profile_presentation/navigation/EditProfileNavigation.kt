package com.prmto.edit_profile_presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.core_presentation.navigation.Screen
import com.prmto.edit_profile_presentation.EditProfileScreen

fun NavGraphBuilder.editProfileNavigation(
    onPopBackStack: () -> Unit
) {
    composable(Screen.EditProfile.route) {
        EditProfileScreen(
            onPopBackStack = onPopBackStack
        )
    }
}