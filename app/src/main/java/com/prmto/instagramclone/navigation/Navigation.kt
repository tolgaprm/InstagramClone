package com.prmto.instagramclone.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.prmto.auth_presentation.navigation.authNestedNavigation
import com.prmto.core_presentation.navigation.NestedNavigation
import com.prmto.core_presentation.navigation.Screen
import com.prmto.home_presentation.navigation.homeNavigation
import com.prmto.navigation.profileNestedNavigation
import com.prmto.reels_presentation.navigation.reelsNavigation
import com.prmto.search_presentation.navigation.searchNavigation
import com.prmto.share_presentation.navigation.shareNavigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SetupNavigation(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = NestedNavigation.Auth.route) {
        homeNavigation(
            onNavigateToMessageScreen = { navController.navigate(Screen.Message.route) },
        )
        shareNavigation()

        profileNestedNavigation(
            onNavigateToSettingScreen = { navController.navigate(Screen.Settings.route) },
            onNavigateToEditProfileScreen = { navController.navigate(Screen.EditProfile.route) },
            onPopBackStack = { navController.popBackStack() },
            onNavigateBack = { navController.navigateUp() }
        )

        authNestedNavigation(
            onNavigateToLogin = { }
        )

        searchNavigation()

        reelsNavigation()

        composable(Screen.Message.route) {
        }
    }
}