package com.prmto.auth_presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.prmto.auth_presentation.util.Constants.UserInfoEmailArgumentName
import com.prmto.core_presentation.navigation.ScreenRoot

sealed class AuthNestedScreens(val route: String) : ScreenRoot() {
    data object Register : AuthNestedScreens("register_screen")
    data object VerifyPhoneNumber : AuthNestedScreens("verify_phone_number_screen}") {
        fun passPhoneNumber(phoneNumber: String): String {
            return "verify_phone_number_screen/$phoneNumber"
        }
    }

    data object Login : AuthNestedScreens("login_screen")

    data object UserInformation :
        AuthNestedScreens("user_information_screen?$UserInfoEmailArgumentName={$UserInfoEmailArgumentName}") {


        fun passEmail(email: String): String {
            return "user_information_screen?$UserInfoEmailArgumentName=$email"
        }

        val arguments = listOf(

            navArgument(UserInfoEmailArgumentName) {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    }
}
