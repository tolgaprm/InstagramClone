package com.prmto.auth_presentation.login.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.auth_presentation.login.LoginRoute
import com.prmto.auth_presentation.navigation.AuthNestedScreens
import com.prmto.auth_presentation.navigation.RegisterNestedScreens

fun NavGraphBuilder.loginNavigation(
    modifier: Modifier = Modifier,
    onNavigateToRegisterScreen: () -> Unit,
    onNavigateToHomeScreen: () -> Unit
) {
    composable(AuthNestedScreens.Login.route) {
        LoginRoute(
            modifier = modifier,
            onNavigateToNestedRegisterScreen = onNavigateToRegisterScreen,
            onNavigateToHomeScreen = onNavigateToHomeScreen
        )
    }
}

fun NavController.navigateToLogin() {
    navigate(AuthNestedScreens.Login.route) {
        popUpTo(RegisterNestedScreens.Register.route) {
            inclusive = true
        }
    }
}