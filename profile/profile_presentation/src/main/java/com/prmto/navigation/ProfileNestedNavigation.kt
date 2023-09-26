package com.prmto.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.prmto.camera.navigation.profileCameraNavigation
import com.prmto.core_presentation.navigation.NestedNavigation
import com.prmto.edit_profile_presentation.navigation.editProfileNavigation
import com.prmto.gallery.navigation.selectProfileImageGalleryNavigation
import com.prmto.profile_presentation.navigation.profileNavigation
import com.prmto.setting_presentation.navigation.settingNavigation

fun NavGraphBuilder.profileNestedNavigation(
    onNavigateToSettingScreen: () -> Unit,
    onNavigateToEditProfileScreen: () -> Unit,
    onNavigateToNestedAuth: () -> Unit,
    onNavigateToProfileCamera: () -> Unit,
    onNavigateToGallery: () -> Unit,
    onPopBackStack: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    navigation(
        startDestination = ProfileScreen.Profile.route,
        route = NestedNavigation.Profile.route
    ) {
        profileNavigation(
            onNavigateToSettingScreen = onNavigateToSettingScreen,
            onNavigateToEditProfileScreen = onNavigateToEditProfileScreen
        )
        editProfileNavigation(
            onPopBackStack = onPopBackStack,
            onNavigateToProfileCamera = onNavigateToProfileCamera,
            onNavigateToGallery = onNavigateToGallery
        )
        settingNavigation(
            onNavigateBack = onNavigateBack,
            onNavigateToEditProfile = onNavigateToEditProfileScreen,
            onNavigateToNestedAuth = onNavigateToNestedAuth
        )
        profileCameraNavigation(
            onPopBacStack = onPopBackStack
        )
        selectProfileImageGalleryNavigation(onPopBacStack = onPopBackStack)
    }
}