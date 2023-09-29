package com.prmto.auth_presentation.navigation

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navigation
import com.prmto.auth_presentation.login.navigation.loginNavigation
import com.prmto.auth_presentation.register.navigation.navigateToRegisterNested
import com.prmto.auth_presentation.register.navigation.registerNestedNavigation
import com.prmto.auth_presentation.user_information.navigation.userInformationNavigation
import com.prmto.auth_presentation.util.AuthTestTags
import com.prmto.core_presentation.navigation.NestedNavigation

fun NavGraphBuilder.authNestedNavigation(
    navController: NavController,
    onNavigateToHomeScreen: () -> Unit
) {
    navigation(
        route = NestedNavigation.Auth.route,
        startDestination = AuthNestedScreens.Login.route
    ) {
        registerNestedNavigation(
            navController = navController
        )

        userInformationNavigation(
            onNavigateToHomeScreen = onNavigateToHomeScreen
        )

        loginNavigation(
            modifier = Modifier.testTag(AuthTestTags.LOGIN_SCREEN),
            onNavigateToRegisterScreen = {
                navController.navigateToRegisterNested()
            },
            onNavigateToHomeScreen = { onNavigateToHomeScreen() }
        )
    }
}

fun NavController.navigateToAuthNested(builder: NavOptionsBuilder.() -> Unit) {
    navigate(NestedNavigation.Auth.route) {
        builder()
    }
}