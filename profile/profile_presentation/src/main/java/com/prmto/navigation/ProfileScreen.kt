package com.prmto.navigation

import com.prmto.core_presentation.navigation.ScreenRoot

sealed class ProfileScreen(val route: String) : ScreenRoot() {
    object Profile : ProfileScreen("profile_screen")

    object EditProfile : ProfileScreen("edit_profile_screen")

    object Settings : ProfileScreen("settings_screen")
}
