package com.prmto.share_presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.core_presentation.navigation.Screen
import com.prmto.share_presentation.ShareScreen

fun NavGraphBuilder.shareNavigation() {
    composable(Screen.Share.route) {
        ShareScreen()
    }
}

fun NavController.navigateToShare() {
    navigate(Screen.Share.route)
}