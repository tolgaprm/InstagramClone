package com.prmto.profile_presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.core_presentation.navigation.Screen
import com.prmto.profile_presentation.ProfileScreen

fun NavGraphBuilder.profileNavigation() {
    composable(Screen.Profile.route) {
        ProfileScreen()
    }
}