package com.prmto.edit_profile_presentation.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.core_presentation.ui.HandleConsumableViewEvents
import com.prmto.edit_profile_presentation.EditProfileScreen
import com.prmto.edit_profile_presentation.EditProfileViewModel
import com.prmto.navigation.ProfileScreen

fun NavGraphBuilder.editProfileNavigation(
    onPopBackStack: () -> Unit,
    onNavigateToProfileCamera: () -> Unit,
    onNavigateToGallery: () -> Unit,
) {
    composable(ProfileScreen.EditProfile.route) {
        val viewModel: EditProfileViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val consumableViewEvents by viewModel.consumableViewEvents.collectAsStateWithLifecycle()
        EditProfileScreen(
            uiState = uiState,
            onPopBackStack = onPopBackStack,
            onEvent = viewModel::onEvent,
            onNavigateToProfileCamera = onNavigateToProfileCamera,
            onNavigateToGallery = onNavigateToGallery
        )

        HandleConsumableViewEvents(
            consumableViewEvents = consumableViewEvents,
            onEventNavigate = {

            },
            onEventConsumed = viewModel::onEventConsumed,
            onPopBackStack = onPopBackStack
        )
    }
}