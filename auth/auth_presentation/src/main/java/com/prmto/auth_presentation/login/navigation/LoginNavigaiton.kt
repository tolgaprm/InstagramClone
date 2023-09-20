package com.prmto.auth_presentation.login.navigation

import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.auth_presentation.login.LoginScreen
import com.prmto.auth_presentation.login.LoginViewModel
import com.prmto.auth_presentation.navigation.AuthNestedScreens
import com.prmto.core_presentation.navigation.Screen
import com.prmto.core_presentation.ui.HandleConsumableViewEvents

fun NavGraphBuilder.loginNavigation(
    modifier: Modifier = Modifier,
    onNavigateToRegisterScreen: () -> Unit,
    onNavigateToHomeScreen: () -> Unit
) {
    composable(AuthNestedScreens.Login.route) {
        val viewModel: LoginViewModel = hiltViewModel()
        val loginUiState by viewModel.uiState.collectAsStateWithLifecycle()
        val consumableViewEvents by viewModel.consumableViewEvents.collectAsStateWithLifecycle()
        LoginScreen(
            modifier = modifier,
            loginUiState = loginUiState,
            onEvent = viewModel::onEvent,
            onNavigateToRegisterScreen = onNavigateToRegisterScreen
        )
        HandleConsumableViewEvents(
            consumableViewEvents = consumableViewEvents,
            onEventNavigate = { route ->
                when (route) {
                    AuthNestedScreens.Register.route -> onNavigateToRegisterScreen()
                    Screen.Home.route -> onNavigateToHomeScreen()
                }
            },
            onEventConsumed = viewModel::onEventConsumed
        )
    }
}