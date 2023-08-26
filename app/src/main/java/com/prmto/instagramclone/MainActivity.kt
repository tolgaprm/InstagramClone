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
import com.prmto.core_domain.repository.FirebaseAuthCoreRepository
import com.prmto.core_presentation.navigation.NestedNavigation
import com.prmto.core_presentation.navigation.Screen
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme
import com.prmto.instagramclone.navigation.InstagramBottomNavigation
import com.prmto.instagramclone.navigation.SetupNavigation
import com.prmto.instagramclone.navigation.rememberBottomNavigationItems
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var firebaseAuthCoreRepository: FirebaseAuthCoreRepository

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
                                navController.navigate(navigationItem.route) {
                                    launchSingleTop = true
                                }

                                bottomNavigationItems = bottomNavigationItems.map {
                                    if (it.route == Screen.Share.route) return@map it.copy(selected = false)
                                    it.copy(selected = it == navigationItem)
                                }
                            }
                        )
                    }
                ) {
                    SetSystemBarColor()
                    val startDestination = firebaseAuthCoreRepository.currentUser()?.let {
                        Screen.Home.route
                    } ?: NestedNavigation.Auth.route

                    SetupNavigation(
                        navController = navController,
                        startDestination = startDestination
                    )
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

