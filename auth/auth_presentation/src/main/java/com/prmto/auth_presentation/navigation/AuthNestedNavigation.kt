package com.prmto.auth_presentation.navigation

import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.prmto.auth_presentation.register.navigation.registerNestedNavigation
import com.prmto.auth_presentation.user_information.UserInformationScreen
import com.prmto.auth_presentation.user_information.UserInformationViewModel
import com.prmto.core_presentation.navigation.NestedNavigation
import com.prmto.core_presentation.util.UiEvent
import com.prmto.core_presentation.util.asString
import kotlinx.coroutines.flow.collectLatest

fun NavGraphBuilder.authNestedNavigation(
    navController: NavController
) {
    navigation(
        route = NestedNavigation.Auth.route,
        startDestination = NestedNavigation.Register.route
    ) {
        registerNestedNavigation(
            navController = navController
        )

        composable(
            route = RegisterScreen.UserInformation.route,
            arguments = RegisterScreen.UserInformation.arguments
        ) {
            val viewModel = hiltViewModel<UserInformationViewModel>()
            UserInformationScreen(
                userInfoData = viewModel.state.value,
                onEvent = viewModel::onEvent
            )

            val context = LocalContext.current

            LaunchedEffect(key1 = true) {
                viewModel.eventFlow.collectLatest { event ->
                    when (event) {
                        is UiEvent.Navigate -> {
                            navController.navigate(event.route)
                        }

                        is UiEvent.ShowMessage -> {
                            Toast.makeText(
                                context,
                                event.uiText.asString(context),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}