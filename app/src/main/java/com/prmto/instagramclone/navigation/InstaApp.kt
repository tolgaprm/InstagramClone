package com.prmto.instagramclone.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prmto.core_presentation.navigation.NestedNavigation
import com.prmto.core_presentation.navigation.Screen
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun InstaApp(
    isUserLoggedIn: Boolean = false
) {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        InstaNavigationActions(navController = navController)
    }
    var startDestination by rememberSaveable(isUserLoggedIn) { mutableStateOf("") }
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val bottomNavigationItems by rememberBottomNavigationItems()
    InstagramCloneTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                InstagramBottomNavigation(
                    currentBackStackEntry = currentBackStackEntry.value,
                    bottomNavigationItems = bottomNavigationItems,
                    instaNavigationActions = navigationActions
                )
            }
        ) {
            SetSystemBarColor()

            startDestination = if (isUserLoggedIn) {
                Screen.Home.route
            } else {
                NestedNavigation.Auth.route
            }

            SetupNavigation(
                navController = navController,
                startDestination = startDestination
            )
        }
    }
}

@Composable
private fun SetSystemBarColor() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    val color = MaterialTheme.colorScheme.background

    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setSystemBarsColor(
            color = color,
            darkIcons = useDarkIcons
        )

        onDispose {

        }
    }
}