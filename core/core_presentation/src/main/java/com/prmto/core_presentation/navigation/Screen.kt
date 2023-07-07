package com.prmto.core_presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object Search : Screen("search_screen")
    object Share : Screen("share_screen")
    object Reels : Screen("reels_screen")
    object Profile : Screen("profile_screen")
    object EditProfile : Screen("edit_profile_screen")
    object Settings : Screen("settings_screen")
    object Message : Screen("message_screen")
}


sealed class NestedNavigation(val route: String) {
    object Profile : NestedNavigation("profile_nested_navigation")
}