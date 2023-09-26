package com.prmto.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.prmto.common.NavArguments
import com.prmto.core_presentation.navigation.ScreenRoot

sealed class ProfileScreen(val route: String) : ScreenRoot() {
    data object Profile :
        ProfileScreen("profile_screen?${NavArguments.USERNAME}={${NavArguments.USERNAME}}") {
        fun passArgs(username: String): String {
            return "$route/${NavArguments.USERNAME}=$username"
        }

        val arguments = listOf(
            navArgument(NavArguments.USERNAME) {
                nullable = true
                defaultValue = null
                type = NavType.StringType
            }
        )
    }

    data object EditProfile : ProfileScreen("edit_profile_screen")

    data object Settings : ProfileScreen("settings_screen")

    data object CameraForProfileImage : ProfileScreen("camera_for_profile_screen")
    data object GalleryForProfileImage : ProfileScreen("gallery_for_profile_screen")
}
