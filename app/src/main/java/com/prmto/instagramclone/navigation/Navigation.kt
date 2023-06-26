package com.prmto.instagramclone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prmto.core_presentation.navigation.Screen
import com.prmto.home_presentation.navigation.homeScreen

@Composable
fun SetupNavigation(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        homeScreen()

        composable(Screen.Search.route) {

        }

        composable(Screen.AddPost.route) {

        }

        composable(Screen.Reels.route) {

        }

        composable(Screen.Profile.route) {

        }
    }
}