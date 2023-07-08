package com.prmto.core_presentation.navigation

abstract class ScreenRoot
sealed class Screen(val route: String) : ScreenRoot() {
    object Home : Screen("home_screen")
    object Search : Screen("search_screen")
    object Share : Screen("share_screen")
    object Reels : Screen("reels_screen")
    object Message : Screen("message_screen")
}


sealed class NestedNavigation(val route: String) {
    object Profile : NestedNavigation("profile_nested_navigation")
    object Auth : NestedNavigation("auth_nested_navigation")
    object Register : NestedNavigation("register_nested_navigation")
}