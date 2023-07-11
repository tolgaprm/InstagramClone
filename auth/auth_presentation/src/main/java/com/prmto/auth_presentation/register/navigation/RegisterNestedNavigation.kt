package com.prmto.auth_presentation.register.navigation

import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.prmto.auth_presentation.navigation.RegisterScreen
import com.prmto.auth_presentation.register.RegisterScreen
import com.prmto.auth_presentation.register.RegisterViewModel
import com.prmto.auth_presentation.register.VerifyPhoneNumberScreen
import com.prmto.core_domain.util.asString
import com.prmto.core_presentation.navigation.NestedNavigation
import com.prmto.core_presentation.util.UiEvent
import com.prmto.core_presentation.util.sharedViewModel
import kotlinx.coroutines.flow.collectLatest

fun NavGraphBuilder.registerNestedNavigation(
    navController: NavController
) {
    navigation(
        startDestination = RegisterScreen.Register.route,
        route = NestedNavigation.Register.route
    ) {
        composable(RegisterScreen.Register.route) {
            val viewModel = it.sharedViewModel<RegisterViewModel>(navController = navController)
            val registerData = viewModel.state.value
            val context = LocalContext.current
            RegisterScreen(
                registerData = registerData,
                onNavigateToLogin = {
                },
                onEvent = viewModel::onEvent
            )

            LaunchedEffect(key1 = true) {
                viewModel.eventFlow.collectLatest { event ->
                    when (event) {
                        is UiEvent.Navigate -> {
                            navController.navigate(event.route)
                        }

                        is UiEvent.ShowMessage -> {
                            Toast.makeText(
                                context,
                                event.uiText.asString(context),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        composable(RegisterScreen.VerifyPhoneNumber.route) {
            val viewModel = it.sharedViewModel<RegisterViewModel>(navController = navController)
            VerifyPhoneNumberScreen(
                phoneNumber = viewModel.state.value.phoneNumberTextField.text,
                verificationCodeValue = viewModel.state.value.verificationCodeTextField,
                onEvent = viewModel::onEvent
            )
        }
    }
}