package com.prmto.profile_presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.navigation.ProfileScreen
import com.prmto.profile_presentation.ProfileScreen
import com.prmto.profile_presentation.ProfileViewModel

fun NavGraphBuilder.profileNavigation(
    onNavigateToSettingScreen: () -> Unit,
    onNavigateToEditProfileScreen: () -> Unit
) {
    composable(
        route = ProfileScreen.Profile.route,
        arguments = ProfileScreen.Profile.arguments
    ) {
        val viewModel: ProfileViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        ProfileScreen(
            uiState = uiState,
            onNavigateToSettingScreen = onNavigateToSettingScreen,
            onNavigateToEditProfileScreen = onNavigateToEditProfileScreen
        )

        LaunchedEffect(key1 = true) {
            if (uiState.isOwnProfile)
                viewModel.getUserDataFromPreferences()
        }
    }
}