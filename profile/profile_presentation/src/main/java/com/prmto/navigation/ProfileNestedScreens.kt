package com.prmto.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

const val profileArgsUsername = "username"

sealed class ProfileNestedScreens(val route: String) {
    data object Profile : ProfileNestedScreens("profile_screen?username={$profileArgsUsername}") {

        fun passArguments(username: String? = null) = "profile_screen?username=$username"

        val arguments = listOf(
            navArgument(profileArgsUsername) {
                nullable = true
                defaultValue = null
                type = NavType.StringType
            }
        )
    }

    data object EditProfile : ProfileNestedScreens("edit_profile_screen")
    data object Setting : ProfileNestedScreens("setting_screen")
    data object ProfileCamera : ProfileNestedScreens("profile_camera_screen")
    data object SelectProfileImageGallery :
        ProfileNestedScreens("select_profile_image_gallery_screen")
}
