package com.prmto.core_presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")

}
