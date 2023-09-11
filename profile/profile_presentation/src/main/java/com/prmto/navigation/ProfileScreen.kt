package com.prmto.navigation

import com.prmto.core_presentation.navigation.ScreenRoot

sealed class ProfileScreen(val route: String) : ScreenRoot() {
    data object Profile : ProfileScreen("profile_screen")

    data object EditProfile : ProfileScreen("edit_profile_screen")

    data object Settings : ProfileScreen("settings_screen")
}
