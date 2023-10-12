package com.prmto.auth_presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.prmto.auth_presentation.R
import com.prmto.auth_presentation.components.AuthButton
import com.prmto.auth_presentation.components.AuthTextField
import com.prmto.auth_presentation.login.event.LoginEvent
import com.prmto.core_presentation.components.InstaProgressIndicator
import com.prmto.core_presentation.navigation.NestedNavigation
import com.prmto.core_presentation.previews.UiModePreview
import com.prmto.core_presentation.ui.HandleConsumableViewEvents
import com.prmto.core_presentation.ui.theme.InstaBlue
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme
import com.prmto.core_presentation.ui.theme.colorBlur
import com.prmto.core_presentation.util.toDp
import com.prmto.core_presentation.R as coreR

@Composable
internal fun LoginRoute(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToNestedRegisterScreen: () -> Unit,
    onNavigateToHomeScreen: () -> Unit
) {
    val loginUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val consumableViewEvents by viewModel.consumableViewEvents.collectAsStateWithLifecycle()
    LoginScreen(
        modifier = modifier,
        loginUiState = loginUiState,
        onEvent = viewModel::onEvent,
        onNavigateToRegisterScreen = onNavigateToNestedRegisterScreen
    )
    HandleConsumableViewEvents(
        consumableViewEvents = consumableViewEvents,
        onEventNavigate = { route ->
            when (route) {
                NestedNavigation.Register.route -> onNavigateToNestedRegisterScreen()
                else -> onNavigateToHomeScreen()
            }
        },
        onEventConsumed = viewModel::onEventConsumed
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    loginUiState: LoginUiState,
    onEvent: (LoginEvent) -> Unit,
    onNavigateToRegisterScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp)
                .padding(top = 32.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = coreR.drawable.logo),
                contentDescription = stringResource(id = coreR.string.logo),
                modifier = Modifier.size(200.dp)
            )

            AuthTextField(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.email_or_username),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                textFieldState = loginUiState.emailOrUserNameTextFieldState,
                onValueChange = {
                    onEvent(LoginEvent.EnteredEmailOrUsername(it))
                }
            )
            Spacer(modifier = Modifier.height(20.dp))

            AuthTextField(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(id = R.string.password),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        onEvent(LoginEvent.OnLoginClicked)
                    }
                ),
                passwordTextFieldState = loginUiState.passwordTextFieldState,
                onValueChange = {
                    onEvent(LoginEvent.EnteredPassword(it))
                },
                onTogglePasswordVisibility = {
                    onEvent(LoginEvent.TogglePasswordVisibility)
                }
            )

            Text(
                text = stringResource(R.string.forgot_password),
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .clickable {
                        onEvent(LoginEvent.OnForgotPasswordClicked)
                    }
                    .padding(4.dp)
                    .align(Alignment.End),
                color = Color.InstaBlue
            )

            Spacer(modifier = Modifier.height(32.dp))

            AuthButton(
                modifier = Modifier.fillMaxWidth(),
                buttonText = stringResource(id = R.string.login),
                onClick = {
                    keyboardController?.hide()
                    onEvent(LoginEvent.OnLoginClicked)
                },
                enabled = !loginUiState.isLoading
            )

            Spacer(modifier = Modifier.height(32.dp))

            LoginDivider()

            Spacer(modifier = Modifier.height(64.dp))

            SignUpSection(
                onNavigateToRegisterScreen = onNavigateToRegisterScreen
            )

            if (loginUiState.isLoading) {
                InstaProgressIndicator()
            }
        }
    }
}

@Composable
private fun LoginDivider() {
    BoxWithConstraints {
        val dividerWidth = ((constraints.maxWidth / 2) - 72).toDp()
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Divider(modifier = Modifier.widthIn(max = dividerWidth))
            Text(
                text = "Or".uppercase(), modifier = Modifier.padding(horizontal = 20.dp)
            )
            Divider(modifier = Modifier.widthIn(max = dividerWidth))
        }
    }
}

@Composable
private fun SignUpSection(
    onNavigateToRegisterScreen: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.don_t_have_an_account),
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 16.sp),
            color = colorBlur()
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = stringResource(R.string.sign_up),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.InstaBlue,
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .clickable {
                    onNavigateToRegisterScreen()
                }
                .padding(4.dp)
        )
    }
}

@UiModePreview
@Composable
fun LoginScreenPreview(
    @PreviewParameter(LoginUiStatePreviewParameterProvider::class) loginUiState: LoginUiState
) {
    InstagramCloneTheme {
        Surface {
            LoginScreen(
                loginUiState = loginUiState,
                onEvent = {},
                onNavigateToRegisterScreen = {}
            )
        }
    }
}