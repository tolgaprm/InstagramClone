package com.prmto.auth_presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.prmto.auth_presentation.register.navigation.registerNestedNavigation
import com.prmto.auth_presentation.user_information.UserInformationScreen
import com.prmto.auth_presentation.user_information.UserInformationViewModel
import com.prmto.core_presentation.navigation.NestedNavigation

fun NavGraphBuilder.authNestedNavigation(
    navController: NavController
) {
    navigation(
        route = NestedNavigation.Auth.route,
        startDestination = NestedNavigation.Register.route
    ) {
        registerNestedNavigation(
            navController = navController
        )

        composable(
            route = RegisterScreen.UserInformation.route,
            arguments = RegisterScreen.UserInformation.arguments
        ) {
            val viewModel = hiltViewModel<UserInformationViewModel>()
            UserInformationScreen()
        }
    }
}