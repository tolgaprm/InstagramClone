package com.prmto.auth_presentation.register.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.prmto.auth_presentation.navigation.AuthNestedScreens
import com.prmto.auth_presentation.register.RegisterViewModel
import com.prmto.auth_presentation.register.screens.RegisterScreen
import com.prmto.auth_presentation.register.screens.VerifyPhoneNumberScreen
import com.prmto.core_presentation.navigation.NestedNavigation
import com.prmto.core_presentation.ui.HandleConsumableViewEvents
import com.prmto.core_presentation.util.sharedViewModel

fun NavGraphBuilder.registerNestedNavigation(
    navController: NavController
) {
    navigation(
        startDestination = AuthNestedScreens.Register.route,
        route = NestedNavigation.Register.route
    ) {
        composable(AuthNestedScreens.Register.route) {
            val viewModel = it.sharedViewModel<RegisterViewModel>(navController = navController)
            val registerUiData by viewModel.uiState.collectAsStateWithLifecycle()
            RegisterScreen(
                registerUiStateData = registerUiData,
                onNavigateToLogin = {
                    navController.navigate(AuthNestedScreens.Login.route) {
                        popUpTo(AuthNestedScreens.Register.route) {
                            inclusive = true
                        }
                    }
                },
                onEvent = viewModel::onEvent
            )

            HandleConsumableViewEvents(
                consumableViewEvents = registerUiData.consumableViewEvents,
                onEventNavigate = navController::navigate,
                onEventConsumed = viewModel::onEventConsumed
            )
        }

        composable(AuthNestedScreens.VerifyPhoneNumber.route) {
            val viewModel = it.sharedViewModel<RegisterViewModel>(navController = navController)
            val registerUiData = viewModel.uiState.collectAsStateWithLifecycle().value
            VerifyPhoneNumberScreen(
                phoneNumber = registerUiData.phoneNumberTextField.text,
                verificationCodeValue = registerUiData.verificationCodeTextField,
                onEvent = viewModel::onEvent
            )
        }
    }
}