package com.prmto.instagramclone.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.prmto.core_presentation.navigation.Screen
import com.prmto.edit_profile_presentation.navigation.editProfileNavigation
import com.prmto.home_presentation.navigation.homeNavigation
import com.prmto.profile_presentation.navigation.profileNavigation
import com.prmto.reels_presentation.navigation.reelsNavigation
import com.prmto.search_presentation.navigation.searchNavigation
import com.prmto.setting_presentation.navigation.settingNavigation
import com.prmto.share_presentation.navigation.shareNavigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SetupNavigation(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        homeNavigation(
            onNavigateToMessageScreen = { navController.navigate(Screen.Message.route) },
        )
        shareNavigation()

        profileNavigation(
            onNavigateToSettingScreen = { navController.navigate(Screen.Settings.route) },
            onNavigateToEditProfileScreen = { navController.navigate(Screen.EditProfile.route) }
        )

        editProfileNavigation(
            onPopBackStack = { navController.popBackStack() }
        )

        settingNavigation(
            onNavigateBack = { navController.navigateUp() },
            onNavigateToEditProfile = { navController.navigate(Screen.EditProfile.route) }
        )

        searchNavigation()

        reelsNavigation()

        composable(Screen.Message.route) {
        }
    }
}