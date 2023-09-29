package com.prmto.auth_presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.prmto.core_presentation.navigation.ScreenRoot

const val userInformationEmailArgumentName = "user_information_email_argument_name"

sealed class AuthNestedScreens(val route: String) : ScreenRoot() {
    data object Login : AuthNestedScreens("login_screen")

    data object UserInformation :
        AuthNestedScreens("user_information_screen?$userInformationEmailArgumentName={$userInformationEmailArgumentName}") {

        fun passEmail(email: String): String {
            return "user_information_screen?$userInformationEmailArgumentName=$email"
        }

        val arguments = listOf(
            navArgument(userInformationEmailArgumentName) {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    }
}
