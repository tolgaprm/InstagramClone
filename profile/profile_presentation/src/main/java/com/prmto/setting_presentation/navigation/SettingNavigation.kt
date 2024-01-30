package com.prmto.setting_presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.navigation.ProfileNestedScreens
import com.prmto.setting_presentation.SettingScreenRoute

internal fun NavGraphBuilder.settingNavigation(
    onNavigateToEditProfile: () -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToNestedAuth: () -> Unit
) {
    composable(ProfileNestedScreens.Setting.route) {
        SettingScreenRoute(
            onNavigateToEditProfile = onNavigateToEditProfile,
            onNavigateBack = onNavigateBack,
            onNavigateToNestedAuth = onNavigateToNestedAuth
        )
    }
}

internal fun NavController.navigateToSetting() {
    navigate(ProfileNestedScreens.Setting.route)
}