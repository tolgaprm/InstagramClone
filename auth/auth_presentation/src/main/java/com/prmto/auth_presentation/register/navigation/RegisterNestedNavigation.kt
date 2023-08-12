package com.prmto.auth_presentation.register.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.prmto.auth_presentation.navigation.RegisterScreen
import com.prmto.auth_presentation.register.RegisterViewModel
import com.prmto.auth_presentation.register.screens.RegisterScreen
import com.prmto.auth_presentation.register.screens.VerifyPhoneNumberScreen
import com.prmto.core_presentation.navigation.NestedNavigation
import com.prmto.core_presentation.util.UiEvent
import com.prmto.core_presentation.util.sharedViewModel

fun NavGraphBuilder.registerNestedNavigation(
    navController: NavController
) {
    navigation(
        startDestination = RegisterScreen.Register.route,
        route = NestedNavigation.Register.route
    ) {
        composable(RegisterScreen.Register.route) {
            val viewModel = it.sharedViewModel<RegisterViewModel>(navController = navController)
            val registerUiData by viewModel.state.collectAsStateWithLifecycle()
            RegisterScreen(
                registerUiStateData = registerUiData,
                onNavigateToLogin = {
                },
                onEvent = viewModel::onEvent
            )

            LaunchedEffect(key1 = registerUiData.consumableViewEvents) {
                if (registerUiData.consumableViewEvents.isEmpty()) return@LaunchedEffect
                registerUiData.consumableViewEvents.forEach { uiEvent ->
                    when (uiEvent) {
                        is UiEvent.Navigate -> {
                            navController.navigate(uiEvent.route)
                            viewModel.onEventConsumed()
                        }

                        else -> return@LaunchedEffect
                    }
                }

            }
        }

        composable(RegisterScreen.VerifyPhoneNumber.route) {
            val viewModel = it.sharedViewModel<RegisterViewModel>(navController = navController)
            val registerUiData = viewModel.state.collectAsStateWithLifecycle().value
            VerifyPhoneNumberScreen(
                phoneNumber = registerUiData.phoneNumberTextField.text,
                verificationCodeValue = registerUiData.verificationCodeTextField,
                onEvent = viewModel::onEvent
            )
        }
    }
}