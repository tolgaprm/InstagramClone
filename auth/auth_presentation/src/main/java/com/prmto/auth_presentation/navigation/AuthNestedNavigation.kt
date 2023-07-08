package com.prmto.auth_presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.prmto.auth_presentation.register.navigation.registerNavigation
import com.prmto.core_presentation.navigation.NestedNavigation
import com.prmto.core_presentation.navigation.Screen

fun NavGraphBuilder.authNestedNavigation(
    onNavigateToLogin: () -> Unit,
) {
    navigation(
        route = NestedNavigation.Auth.route,
        startDestination = Screen.Register.route
    ) {
        registerNavigation(
            onNavigateToLogin = onNavigateToLogin
        )
    }
}