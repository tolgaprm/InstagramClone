package com.prmto.search_presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.core_presentation.navigation.Screen
import com.prmto.search_presentation.SearchScreen

fun NavGraphBuilder.searchNavigation() {
    composable(Screen.Search.route) {
        SearchScreen()
    }
}

fun NavController.navigateToSearch() {
    navigate(Screen.Search.route)
}