package com.prmto.home_presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.prmto.home_presentation.components.HomeTopBar

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToMessageScreen: () -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            HomeTopBar(
                onNavigateToMessageScreen = onNavigateToMessageScreen,
            )
        }
    ) {

    }
}
