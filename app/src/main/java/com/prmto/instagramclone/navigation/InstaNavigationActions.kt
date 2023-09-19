package com.prmto.instagramclone.navigation

import androidx.navigation.NavHostController

class InstaNavigationActions(private val navController: NavHostController) {
    fun navigateToTopLevelDestination(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            navController.graph.startDestinationRoute?.let { popUpTo(it) }
        }
    }
}