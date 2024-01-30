package com.prmto.profile_presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.navigation.ProfileNestedScreens
import com.prmto.profile_presentation.ProfileRoute

internal fun NavGraphBuilder.profileNavigation(
    onNavigateToSettingScreen: () -> Unit,
    onNavigateToEditProfileScreen: () -> Unit
) {
    composable(
        route = ProfileNestedScreens.Profile.route,
        arguments = ProfileNestedScreens.Profile.arguments
    ) {
        ProfileRoute(
            onNavigateToSettingScreen = onNavigateToSettingScreen,
            onNavigateToEditProfileScreen = onNavigateToEditProfileScreen
        )
    }
}

internal fun NavController.navigateToProfile(username: String? = null) {
    navigate(ProfileNestedScreens.Profile.passArguments(username))
}