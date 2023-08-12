package com.prmto.auth_presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.prmto.auth_presentation.register.navigation.registerNestedNavigation
import com.prmto.auth_presentation.user_information.navigation.userInformationNavigation
import com.prmto.core_presentation.navigation.NestedNavigation

fun NavGraphBuilder.authNestedNavigation(
    navController: NavController,
    onNavigateToHomeScreen: () -> Unit
) {
    navigation(
        route = NestedNavigation.Auth.route,
        startDestination = NestedNavigation.Register.route
    ) {
        registerNestedNavigation(
            navController = navController
        )

        userInformationNavigation(
            onNavigateToHomeScreen = onNavigateToHomeScreen
        )
    }
}