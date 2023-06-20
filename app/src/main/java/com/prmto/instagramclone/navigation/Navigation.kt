package com.prmto.instagramclone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.prmto.core_presentation.navigation.Screen
import com.prmto.home_presentation.navigation.homeScreen

@Composable
fun setupNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        homeScreen()
    }
}