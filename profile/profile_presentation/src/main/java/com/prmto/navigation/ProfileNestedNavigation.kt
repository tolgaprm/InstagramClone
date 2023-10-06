package com.prmto.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.prmto.camera.navigation.navigateToProfileCamera
import com.prmto.camera.navigation.profileCameraNavigation
import com.prmto.core_presentation.navigation.NestedNavigation
import com.prmto.edit_profile_presentation.navigation.editProfileNavigation
import com.prmto.edit_profile_presentation.navigation.navigateToEditProfile
import com.prmto.gallery.navigation.navigateToProfileImageGallery
import com.prmto.gallery.navigation.selectProfileImageGalleryNavigation
import com.prmto.profile_presentation.navigation.profileNavigation
import com.prmto.setting_presentation.navigation.navigateToSetting
import com.prmto.setting_presentation.navigation.settingNavigation

fun NavGraphBuilder.profileNestedNavigation(
    navController: NavController,
    onNavigateToNestedAuth: () -> Unit
) {
    navigation(
        startDestination = ProfileNestedScreens.Profile.route,
        route = NestedNavigation.Profile.route
    ) {
        profileNavigation(
            onNavigateToSettingScreen = { navController.navigateToSetting() },
            onNavigateToEditProfileScreen = { navController.navigateToEditProfile() }
        )
        editProfileNavigation(
            onPopBackStack = { navController.popBackStack() },
            onNavigateToProfileCamera = { navController.navigateToProfileCamera() },
            onNavigateToGallery = { navController.navigateToProfileImageGallery() }
        )
        settingNavigation(
            onNavigateBack = { navController.navigateUp() },
            onNavigateToEditProfile = { navController.navigateToEditProfile() },
            onNavigateToNestedAuth = onNavigateToNestedAuth
        )
        profileCameraNavigation(
            onPopBacStack = { navController.popBackStack() }
        )
        selectProfileImageGalleryNavigation(onPopBacStack = { navController.popBackStack() })
    }
}

fun NavController.navigateToProfileNested() {
    navigate(NestedNavigation.Profile.route)
}