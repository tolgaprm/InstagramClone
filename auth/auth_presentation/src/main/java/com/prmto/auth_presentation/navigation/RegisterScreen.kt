package com.prmto.auth_presentation.navigation

import com.prmto.core_presentation.navigation.ScreenRoot

sealed class RegisterScreen(val route: String) : ScreenRoot() {
    object Register : RegisterScreen("register_screen")
    object VerifyPhoneNumber : RegisterScreen("verify_phone_number_screen}") {
        fun passPhoneNumber(phoneNumber: String): String {
            return "verify_phone_number_screen/$phoneNumber"
        }
    }
}
