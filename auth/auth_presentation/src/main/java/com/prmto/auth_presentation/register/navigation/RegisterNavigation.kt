package com.prmto.auth_presentation.register.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.auth_presentation.register.RegisterScreen
import com.prmto.auth_presentation.register.RegisterViewModel
import com.prmto.core_presentation.navigation.Screen

fun NavGraphBuilder.registerNavigation(
    onNavigateToLogin: () -> Unit,
) {
    composable(Screen.Register.route) {
        val viewModel = hiltViewModel<RegisterViewModel>()
        val registerData = viewModel.state.value
        RegisterScreen(
            registerData = registerData,
            onNavigateToLogin = onNavigateToLogin,
            onEvent = viewModel::onEvent,
        )
    }
}