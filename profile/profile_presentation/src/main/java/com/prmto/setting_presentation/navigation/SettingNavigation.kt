package com.prmto.setting_presentation.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.core_presentation.navigation.NestedNavigation
import com.prmto.core_presentation.ui.HandleConsumableViewEvents
import com.prmto.navigation.ProfileScreen
import com.prmto.setting_presentation.SettingScreen
import com.prmto.setting_presentation.SettingViewModel

fun NavGraphBuilder.settingNavigation(
    onNavigateToEditProfile: () -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToNestedAuth: () -> Unit
) {
    composable(ProfileScreen.Settings.route) {
        val viewModel: SettingViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        SettingScreen(
            onNavigateBack = onNavigateBack,
            onNavigateToEditProfile = onNavigateToEditProfile,
            onEvent = viewModel::onEvent
        )

        HandleConsumableViewEvents(
            consumableViewEvents = uiState.consumableViewEvents,
            onEventNavigate = { route ->
                when (route) {
                    NestedNavigation.Auth.route -> onNavigateToNestedAuth()
                }
            },
            onEventConsumed = viewModel::onEventConsumed
        )
    }
}