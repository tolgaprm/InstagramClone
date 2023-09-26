package com.prmto.gallery.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.gallery.ProfileImageGalleryScreen
import com.prmto.gallery.SelectProfileImageGalleryViewModel
import com.prmto.navigation.ProfileScreen

fun NavGraphBuilder.selectProfileImageGalleryNavigation(onPopBacStack: () -> Unit) {
    composable(ProfileScreen.GalleryForProfileImage.route) {
        val viewModel: SelectProfileImageGalleryViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        ProfileImageGalleryScreen(
            uiState = uiState,
            onPopBackStack = onPopBacStack,
            onEvent = viewModel::onEvent
        )
    }
}