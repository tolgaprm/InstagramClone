package com.prmto.auth_presentation.register.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.prmto.auth_presentation.navigation.RegisterScreen
import com.prmto.auth_presentation.register.RegisterScreen
import com.prmto.auth_presentation.register.RegisterViewModel
import com.prmto.core_presentation.navigation.NestedNavigation

fun NavGraphBuilder.registerNestedNavigation(
    onNavigateToLogin: () -> Unit,
) {
    navigation(
        startDestination = RegisterScreen.Register.route,
        route = NestedNavigation.Register.route
    ) {
        composable(RegisterScreen.Register.route) {
            val viewModel = hiltViewModel<RegisterViewModel>()
            val registerData = viewModel.state.value
            RegisterScreen(
                registerData = registerData,
                onNavigateToLogin = onNavigateToLogin,
                onEvent = viewModel::onEvent,
            )
        }
    }
}