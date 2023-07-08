package com.prmto.auth_presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.prmto.auth_presentation.register.navigation.registerNestedNavigation
import com.prmto.core_presentation.navigation.NestedNavigation

fun NavGraphBuilder.authNestedNavigation(
    onNavigateToLogin: () -> Unit,
) {
    navigation(
        route = NestedNavigation.Auth.route,
        startDestination = NestedNavigation.Register.route
    ) {

        registerNestedNavigation(
            onNavigateToLogin = onNavigateToLogin
        )
    }
}