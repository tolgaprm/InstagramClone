package com.prmto.home_presentation.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.prmto.core_presentation.navigation.Screen
import com.prmto.home_presentation.HomeScreen

fun NavGraphBuilder.homeNavigation(
    modifier: Modifier = Modifier,
    onNavigateToMessageScreen: () -> Unit
) {
    composable(Screen.Home.route) {
        HomeScreen(
            modifier = modifier,
            onNavigateToMessageScreen = onNavigateToMessageScreen,
        )
    }
}

fun NavController.navigateToHome(builder: NavOptionsBuilder.() -> Unit) {
    navigate(Screen.Home.route) {
        builder()
    }
}