package com.prmto.auth_presentation.user_information

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.prmto.auth_presentation.R
import com.prmto.auth_presentation.components.AuthButton
import com.prmto.auth_presentation.components.AuthTextField
import com.prmto.auth_presentation.user_information.event.UserInfoEvents
import com.prmto.core_presentation.components.InstaProgressIndicator
import com.prmto.core_presentation.navigation.Screen
import com.prmto.core_presentation.previews.UiModePreview
import com.prmto.core_presentation.ui.HandleConsumableViewEvents
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme

@Composable
fun UserInformationRoute(
    modifier: Modifier = Modifier,
    viewModel: UserInformationViewModel = hiltViewModel(),
    onNavigateToHomeScreen: () -> Unit
) {
    val userInfoUiData by viewModel.state.collectAsStateWithLifecycle()
    val consumableViewEvents by viewModel.consumableViewEvents.collectAsStateWithLifecycle()
    UserInformationScreen(
        modifier = modifier,
        userInfoUiData = userInfoUiData,
        onEvent = viewModel::onEvent
    )
    HandleConsumableViewEvents(
        consumableViewEvents = consumableViewEvents,
        onEventNavigate = { route ->
            if (route == Screen.Home.route) {
                onNavigateToHomeScreen()
            }
        },
        onEventConsumed = viewModel::onEventConsumed
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun UserInformationScreen(
    modifier: Modifier = Modifier,
    userInfoUiData: UserInfoUiData,
    onEvent: (UserInfoEvents) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 68.dp)
            .padding(horizontal = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.user_information),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.padding(top = 16.dp))

            AuthTextField(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.name_and_surname),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                textFieldState = userInfoUiData.fullNameTextField,
                onValueChange = {
                    onEvent(UserInfoEvents.EnterFullName(it))
                }
            )

            AuthTextField(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.username),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                textFieldState = userInfoUiData.usernameTextField,
                onValueChange = {
                    onEvent(UserInfoEvents.EnterUsername(it))
                }
            )

            AuthTextField(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.password),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        onEvent(UserInfoEvents.Register)
                    }
                ),
                passwordTextFieldState = userInfoUiData.passwordTextField,
                onValueChange = {
                    onEvent(UserInfoEvents.EnterPassword(it))
                },
                onTogglePasswordVisibility = {
                    onEvent(UserInfoEvents.TogglePasswordVisibility)
                }
            )

            AuthButton(
                modifier = Modifier.fillMaxWidth(),
                buttonText = stringResource(R.string.register),
                enabled = !userInfoUiData.isRegistering,
                onClick = {
                    onEvent(UserInfoEvents.Register)
                    keyboardController?.hide()
                }
            )

            if (userInfoUiData.isRegistering) {
                InstaProgressIndicator()
            }
        }
    }
}

@UiModePreview
@Composable
fun UserInformationScreenPreview(
    @PreviewParameter(UserInfoUiDataPreviewProvider::class) UserInfoUiData: UserInfoUiData
) {
    InstagramCloneTheme {
        Surface {
            UserInformationScreen(
                userInfoUiData = UserInfoUiData,
                onEvent = {}
            )
        }
    }
}