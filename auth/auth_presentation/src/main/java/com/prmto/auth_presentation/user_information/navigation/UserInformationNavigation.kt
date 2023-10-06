package com.prmto.auth_presentation.user_information.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.auth_presentation.navigation.AuthNestedScreens
import com.prmto.auth_presentation.navigation.userInformationEmailArgumentName
import com.prmto.auth_presentation.user_information.UserInformationRoute


fun NavGraphBuilder.userInformationNavigation(
    onNavigateToHomeScreen: () -> Unit
) {
    composable(
        route = AuthNestedScreens.UserInformation.route,
        arguments = AuthNestedScreens.UserInformation.arguments
    ) {
        UserInformationRoute(
            onNavigateToHomeScreen = onNavigateToHomeScreen
        )
    }
}

fun NavController.navigateUserInformation(
    email: String = ""
) {
    navigate(
        route = "${AuthNestedScreens.UserInformation.route}?arg=$email"
    )
}

internal class UserInformationArgs(
    val email: String?
) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        email = savedStateHandle[userInformationEmailArgumentName]
    )
}