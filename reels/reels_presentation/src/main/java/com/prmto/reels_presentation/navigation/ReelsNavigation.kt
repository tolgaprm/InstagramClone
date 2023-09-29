package com.prmto.reels_presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.core_presentation.navigation.Screen
import com.prmto.reels_presentation.ReelsScreen

fun NavGraphBuilder.reelsNavigation() {
    composable(Screen.Reels.route) {
        ReelsScreen()
    }
}

fun NavController.navigateToReels() {
    navigate(Screen.Reels.route)
}