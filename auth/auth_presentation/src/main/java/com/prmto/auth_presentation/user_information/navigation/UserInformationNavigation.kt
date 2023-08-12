package com.prmto.auth_presentation.user_information.navigation

import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prmto.auth_presentation.navigation.RegisterScreen
import com.prmto.auth_presentation.user_information.UserInformationScreen
import com.prmto.auth_presentation.user_information.UserInformationViewModel
import com.prmto.core_domain.constants.asString
import com.prmto.core_presentation.navigation.Screen
import com.prmto.core_presentation.util.UiEvent

fun NavGraphBuilder.userInformationNavigation(
    onNavigateToHomeScreen: () -> Unit
) {
    composable(
        route = RegisterScreen.UserInformation.route,
        arguments = RegisterScreen.UserInformation.arguments,
    ) {
        val viewModel = hiltViewModel<UserInformationViewModel>()
        val userInfoUiData by viewModel.state.collectAsStateWithLifecycle()
        UserInformationScreen(
            userInfoUiData = userInfoUiData,
            onEvent = viewModel::onEvent
        )
        val context = LocalContext.current
        LaunchedEffect(key1 = userInfoUiData.consumableViewEvents) {
            if (userInfoUiData.consumableViewEvents.isEmpty()) return@LaunchedEffect
            userInfoUiData.consumableViewEvents.forEach { event ->
                when (event) {
                    is UiEvent.Navigate -> {
                        if (event.route == Screen.Home.route) {
                            onNavigateToHomeScreen()
                        }
                        viewModel.onEventConsumed()
                    }

                    is UiEvent.ShowMessage -> {
                        Toast.makeText(
                            context,
                            event.uiText.asString(context),
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.onEventConsumed()
                    }

                    else -> {
                        return@LaunchedEffect
                    }
                }
            }
        }
    }
}