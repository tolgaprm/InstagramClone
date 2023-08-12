package com.prmto.auth_presentation.login.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.auth_presentation.login.LoginScreen
import com.prmto.auth_presentation.login.LoginViewModel
import com.prmto.auth_presentation.navigation.AuthNestedScreens

fun NavGraphBuilder.loginNavigation(
    onNavigateToRegisterScreen: () -> Unit
) {

    composable(AuthNestedScreens.Login.route) {
        val viewModel: LoginViewModel = hiltViewModel()
        val loginUiState by viewModel.loginUiState.collectAsStateWithLifecycle()
        LoginScreen(
            loginUiState = loginUiState,
            onEvent = viewModel::onEvent,
            onNavigateToRegisterScreen = onNavigateToRegisterScreen
        )
    }
}