package com.prmto.auth_presentation.navigation

sealed class RegisterNestedScreens {
    data object Register : AuthNestedScreens("register_screen")
    data object VerifyPhoneNumber : AuthNestedScreens("verify_phone_number_screen}") {
        fun passPhoneNumber(phoneNumber: String): String {
            return "verify_phone_number_screen/$phoneNumber"
        }
    }
}