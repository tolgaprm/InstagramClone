package com.prmto.core_presentation.navigation

abstract class ScreenRoot
sealed class Screen(val route: String) : ScreenRoot() {
    data object Home : Screen("home_screen")
    data object Search : Screen("search_screen")
    data object Reels : Screen("reels_screen")
    data object Message : Screen("message_screen")
}


sealed class NestedNavigation(val route: String) {
    data object Profile : NestedNavigation("profile_nested_navigation")
    data object Auth : NestedNavigation("auth_nested_navigation")
    data object Register : NestedNavigation("register_nested_navigation")
    data object Share : NestedNavigation("share_nested_navigation")
}