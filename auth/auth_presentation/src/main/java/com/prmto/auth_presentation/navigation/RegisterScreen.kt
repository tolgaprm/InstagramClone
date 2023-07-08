package com.prmto.auth_presentation.navigation

import com.prmto.core_presentation.navigation.ScreenRoot

sealed class RegisterScreen(val route: String) : ScreenRoot() {
    object Register : RegisterScreen("register_screen")
}
