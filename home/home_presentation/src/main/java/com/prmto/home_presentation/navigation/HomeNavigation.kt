package com.prmto.home_presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.core_presentation.navigation.Screen
import com.prmto.home_presentation.HomeScreen


fun NavGraphBuilder.homeScreen() {
    composable(Screen.Home.route) {
        HomeScreen()
    }
}