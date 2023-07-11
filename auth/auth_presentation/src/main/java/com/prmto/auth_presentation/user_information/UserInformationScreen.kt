package com.prmto.auth_presentation.user_information

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prmto.auth_presentation.R
import com.prmto.auth_presentation.components.AuthButton
import com.prmto.auth_presentation.components.AuthTextField

@Composable
fun UserInformationScreen(
    userInfoData: UserInfoData,
    onEvent: (UserInfoEvents) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 68.dp)
            .padding(horizontal = 16.dp),
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
            textFieldState = userInfoData.fullNameTextField,
            onValueChange = {
                onEvent(UserInfoEvents.EnterFullName(it))
            }
        )

        AuthTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.username),
            textFieldState = userInfoData.usernameTextField,
            onValueChange = {
                onEvent(UserInfoEvents.EnterUsername(it))
            }
        )

        AuthTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.password),
            passwordTextFieldState = userInfoData.passwordTextField,
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
            onClick = {
                onEvent(UserInfoEvents.Register)
            }
        )
    }
}