package com.prmto.instagramclone

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prmto.instagramclone.navigation.InstagramBottomNavigation
import com.prmto.instagramclone.navigation.SetupNavigation
import com.prmto.instagramclone.navigation.rememberBottomNavigationItems
import com.prmto.instagramclone.ui.theme.InstagramCloneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val currentBackStackEntry = navController.currentBackStackEntryAsState()
            var bottomNavigationItems by rememberBottomNavigationItems()
            InstagramCloneTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        InstagramBottomNavigation(
                            currentBackStackEntry = currentBackStackEntry.value,
                            bottomNavigationItems = bottomNavigationItems,
                            onNavigate = { navigationItem ->
                                navController.navigate(navigationItem.screen.route) {
                                    launchSingleTop = true
                                }

                                bottomNavigationItems = bottomNavigationItems.map {
                                    it.copy(selected = it == navigationItem)
                                }
                            }
                        )
                    }
                ) {
                    SetSystemBarColor()
                    SetupNavigation(navController = navController)
                }
            }
        }
    }
}

@Composable
fun SetSystemBarColor() {
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

