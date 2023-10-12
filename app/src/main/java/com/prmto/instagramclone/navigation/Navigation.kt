package com.prmto.instagramclone.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.prmto.auth_presentation.navigation.authNestedNavigation
import com.prmto.auth_presentation.navigation.navigateToAuthNested
import com.prmto.core_presentation.navigation.NestedNavigation
import com.prmto.home_presentation.navigation.homeNavigation
import com.prmto.home_presentation.navigation.navigateToHome
import com.prmto.home_presentation.util.HomeTestTags
import com.prmto.navigation.profileNestedNavigation
import com.prmto.reels_presentation.navigation.reelsNavigation
import com.prmto.search_presentation.navigation.searchNavigation
import com.prmto.share_presentation.navigation.shareNavigation

@Composable
fun SetupNavigation(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        homeNavigation(
            modifier = Modifier.testTag(HomeTestTags.HOME_SCREEN),
            onNavigateToMessageScreen = { }, // TODO
        )
        shareNavigation()

        profileNestedNavigation(
            navController = navController,
            onNavigateToNestedAuth = {
                navController.navigateToAuthNested {
                    popUpTo(NestedNavigation.Profile.route) {
                        inclusive = true
                    }
                }
            }
        )

        authNestedNavigation(
            navController = navController,
            onNavigateToHomeScreen = {
                navController.navigateToHome {
                    popUpTo(NestedNavigation.Auth.route) {
                        inclusive = true
                    }
                }
            }
        )

        searchNavigation()

        reelsNavigation()

        composable("messageRoute") {}
    }
}