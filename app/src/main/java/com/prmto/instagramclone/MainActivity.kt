package com.prmto.instagramclone

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.prmto.instagramclone.navigation.InstaApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            ChangeSystemBarsTheme(!isSystemInDarkTheme())
            val viewModel: MainViewModel = hiltViewModel()
            InstaApp(
                isUserLoggedIn = viewModel.isUserLoggedIn()
            )
        }
    }


    @Composable
    private fun ChangeSystemBarsTheme(
        isLightTheme: Boolean
    ) {
        val color = Color.White.toArgb()
        LaunchedEffect(
            key1 = isLightTheme
        ) {
            if (isLightTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.light(
                        scrim = color,
                        darkScrim = color
                    ),
                    navigationBarStyle = SystemBarStyle.light(
                        scrim = color,
                        darkScrim = color
                    )
                )
            } else {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.dark(
                        scrim = Color.Black.toArgb()
                    ),
                    navigationBarStyle = SystemBarStyle.dark(
                        scrim = Color.Black.toArgb()
                    )
                )
            }
        }
    }
}