package com.prmto.setting_presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.navigation.ProfileScreen
import com.prmto.setting_presentation.SettingScreen

fun NavGraphBuilder.settingNavigation(
    onNavigateToEditProfile: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    composable(ProfileScreen.Settings.route) {
        SettingScreen(
            onNavigateBack = onNavigateBack,
            onNavigateToEditProfile = onNavigateToEditProfile
        )
    }
}