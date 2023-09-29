package com.prmto.auth_presentation.register.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.prmto.auth_presentation.navigation.RegisterNestedScreens
import com.prmto.auth_presentation.register.RegisterViewModel
import com.prmto.auth_presentation.register.screens.RegisterScreen
import com.prmto.core_presentation.ui.HandleConsumableViewEvents

fun NavController.navigateToRegister() {
    navigate(RegisterNestedScreens.Register.route)
}

@Composable
internal fun RegisterRoute(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel,
    onNavigateToLogin: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val registerUiData by viewModel.uiState.collectAsStateWithLifecycle()
    val consumableViewEvents by viewModel.consumableViewEvents.collectAsStateWithLifecycle()
    RegisterScreen(
        modifier = modifier,
        registerUiStateData = registerUiData,
        onNavigateToLogin = onNavigateToLogin,
        onEvent = viewModel::onEvent
    )

    HandleConsumableViewEvents(
        consumableViewEvents = consumableViewEvents,
        onEventNavigate = onNavigate,
        onEventConsumed = viewModel::onEventConsumed
    )
}