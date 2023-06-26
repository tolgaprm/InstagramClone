package com.prmto.core_presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object Search : Screen("search_screen")
    object AddPost : Screen("add_post_screen")
    object Reels : Screen("reels_screen")
    object Profile : Screen("profile_screen")
}
