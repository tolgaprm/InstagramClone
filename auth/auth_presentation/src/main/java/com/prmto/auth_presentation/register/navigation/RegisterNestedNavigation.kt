package com.prmto.auth_presentation.register.navigation

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.prmto.auth_presentation.login.navigation.navigateToLogin
import com.prmto.auth_presentation.navigation.RegisterNestedScreens
import com.prmto.auth_presentation.register.RegisterViewModel
import com.prmto.core_presentation.navigation.NestedNavigation
import com.prmto.core_presentation.util.sharedViewModel

fun NavGraphBuilder.registerNestedNavigation(
    navController: NavController
) {
    navigation(
        startDestination = RegisterNestedScreens.Register.route,
        route = NestedNavigation.Register.route
    ) {
        composable(RegisterNestedScreens.Register.route) {
            val viewModel = it.sharedViewModel<RegisterViewModel>(navController = navController)
            RegisterRoute(
                viewModel = viewModel,
                onNavigateToLogin = { navController.navigateToLogin() },
                onNavigate = navController::navigate
            )
        }

        composable(RegisterNestedScreens.VerifyPhoneNumber.route) {
            val viewModel = it.sharedViewModel<RegisterViewModel>(navController = navController)
            val registerUiData = viewModel.uiState.collectAsStateWithLifecycle().value
            VerifyPhoneNumberRoute(
                phoneNumber = registerUiData.phoneNumberTextField.text,
                verificationCodeValue = registerUiData.verificationCodeTextField,
                onEvent = viewModel::onEvent
            )
        }
    }
}

fun NavController.navigateToRegisterNested() {
    navigate(NestedNavigation.Register.route)
}