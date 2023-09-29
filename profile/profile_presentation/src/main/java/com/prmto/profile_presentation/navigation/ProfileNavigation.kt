package com.prmto.profile_presentation.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.navigation.ProfileNestedScreens
import com.prmto.navigation.profileArgsUsername
import com.prmto.profile_presentation.ProfileRoute

fun NavGraphBuilder.profileNavigation(
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

fun NavController.navigateToProfile(username: String? = null) {
    navigate(ProfileNestedScreens.Profile.passArguments(username))
}

internal class ProfileArgs(val username: String? = null) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        username = savedStateHandle[profileArgsUsername]
    )
}