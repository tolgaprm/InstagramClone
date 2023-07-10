package com.prmto.auth_presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.prmto.auth_presentation.util.Constants.UserInfoEmailArgumentName
import com.prmto.auth_presentation.util.Constants.UserInfoPhoneArgumentName
import com.prmto.core_presentation.navigation.ScreenRoot

sealed class RegisterScreen(val route: String) : ScreenRoot() {
    object Register : RegisterScreen("register_screen")
    object VerifyPhoneNumber : RegisterScreen("verify_phone_number_screen}") {
        fun passPhoneNumber(phoneNumber: String): String {
            return "verify_phone_number_screen/$phoneNumber"
        }
    }

    object UserInformation :
        RegisterScreen("user_information_screen?$UserInfoPhoneArgumentName={$UserInfoPhoneArgumentName}&$UserInfoEmailArgumentName={$UserInfoEmailArgumentName}") {

        fun passPhoneNumber(phoneNumber: String): String {
            return "user_information_screen?$UserInfoPhoneArgumentName=$phoneNumber"
        }

        fun passEmail(email: String): String {
            return "user_information_screen?$UserInfoEmailArgumentName=$email"
        }

        val arguments = listOf(
            navArgument(UserInfoPhoneArgumentName) {
                type = NavType.StringType
                defaultValue = ""
            },
            navArgument(UserInfoEmailArgumentName) {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    }
}