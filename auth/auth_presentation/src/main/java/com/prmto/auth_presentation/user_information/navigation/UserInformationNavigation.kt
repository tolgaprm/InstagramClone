package com.prmto.auth_presentation.user_information.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.auth_presentation.navigation.AuthNestedScreens
import com.prmto.auth_presentation.user_information.UserInformationScreen
import com.prmto.auth_presentation.user_information.UserInformationViewModel
import com.prmto.core_presentation.navigation.Screen
import com.prmto.core_presentation.ui.HandleConsumableViewEvents

fun NavGraphBuilder.userInformationNavigation(
    onNavigateToHomeScreen: () -> Unit
) {
    composable(
        route = AuthNestedScreens.UserInformation.route,
        arguments = AuthNestedScreens.UserInformation.arguments,
    ) {
        val viewModel = hiltViewModel<UserInformationViewModel>()
        val userInfoUiData by viewModel.state.collectAsStateWithLifecycle()
        val consumableViewEvents by viewModel.consumableViewEvents.collectAsStateWithLifecycle()
        UserInformationScreen(
            userInfoUiData = userInfoUiData,
            onEvent = viewModel::onEvent
        )
        HandleConsumableViewEvents(
            consumableViewEvents = consumableViewEvents,
            onEventNavigate = { route ->
                if (route == Screen.Home.route) {
                    onNavigateToHomeScreen()
                }
            },
            onEventConsumed = viewModel::onEventConsumed
        )
    }
}