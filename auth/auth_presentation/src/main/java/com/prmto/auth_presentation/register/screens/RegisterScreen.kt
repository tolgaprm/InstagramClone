package com.prmto.auth_presentation.register.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.prmto.auth_presentation.R
import com.prmto.auth_presentation.components.AuthButton
import com.prmto.auth_presentation.components.AuthTextField
import com.prmto.auth_presentation.register.RegisterUiStateData
import com.prmto.auth_presentation.register.RegisterUiStateDataPreviewProvider
import com.prmto.auth_presentation.register.SelectedTab
import com.prmto.auth_presentation.register.components.RegisterScreenBottomSection
import com.prmto.auth_presentation.register.event.RegisterEvent
import com.prmto.auth_presentation.register.isPhoneNumberSelected
import com.prmto.core_presentation.previews.UiModePreview
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme
import com.prmto.core_presentation.R as CoreRes

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun RegisterScreen(
    modifier: Modifier = Modifier,
    registerUiStateData: RegisterUiStateData,
    onNavigateToLogin: () -> Unit,
    onEvent: (RegisterEvent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = modifier,
        bottomBar = {
            RegisterScreenBottomSection(
                onNavigateToLogin = onNavigateToLogin
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RegisterScreenTopSection(
                registerUiStateData = registerUiStateData,
                onEvent = onEvent
            )
            Spacer(modifier = Modifier.height(20.dp))

            if (registerUiStateData.isPhoneNumberSelected()) {
                AuthTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onValueChange = {
                        onEvent(RegisterEvent.EnteredPhoneNumber(it))
                    },
                    label = stringResource(id = R.string.phone_number),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    textFieldState = registerUiStateData.phoneNumberTextField
                )
            } else {
                AuthTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textFieldState = registerUiStateData.emailTextField,
                    onValueChange = {
                        onEvent(RegisterEvent.EnteredEmail(it))
                    },
                    label = stringResource(id = R.string.email),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            AuthButton(
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = registerUiStateData.isNextButtonEnabled,
                buttonText = stringResource(R.string.next),
                onClick = {
                    onEvent(RegisterEvent.OnClickNext)
                    keyboardController?.hide()
                }
            )
        }
    }
}


@Composable
fun RegisterScreenTopSection(
    registerUiStateData: RegisterUiStateData,
    onEvent: (RegisterEvent) -> Unit
) {
    Image(
        modifier = Modifier
            .padding(top = 48.dp)
            .size(150.dp),
        painter = painterResource(id = CoreRes.drawable.account),
        contentDescription = null
    )

    TabRow(
        modifier = Modifier
            .padding(top = 16.dp),
        selectedTabIndex = registerUiStateData.selectedTab.ordinal,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[registerUiStateData.selectedTab.ordinal]),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    ) {
        InstaRegisterTab(
            selected = registerUiStateData.isPhoneNumberSelected(),
            tabName = stringResource(R.string.phone_number),
            onClick = {
                onEvent(RegisterEvent.OnClickTab(SelectedTab.PHONE_NUMBER))
            }
        )

        InstaRegisterTab(
            selected = !registerUiStateData.isPhoneNumberSelected(),
            tabName = stringResource(R.string.email),
            onClick = {
                onEvent(RegisterEvent.OnClickTab(SelectedTab.EMAIL))
            }
        )
    }
}

@Composable
private fun InstaRegisterTab(
    modifier: Modifier = Modifier,
    selected: Boolean,
    tabName: String,
    onClick: () -> Unit
) {
    Tab(
        modifier = modifier.height(56.dp),
        selected = selected,
        selectedContentColor = MaterialTheme.colorScheme.onBackground,
        unselectedContentColor = Color.Gray,
        onClick = onClick
    ) {
        Text(
            text = tabName,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

@UiModePreview
@Composable
fun RegisterScreenPreview(
    @PreviewParameter(RegisterUiStateDataPreviewProvider::class) registerUiStateData: RegisterUiStateData
) {
    InstagramCloneTheme {
        Surface {
            RegisterScreen(
                registerUiStateData = registerUiStateData,
                onNavigateToLogin = {},
                onEvent = {}
            )
        }
    }
}