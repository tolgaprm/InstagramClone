package com.prmto.auth_presentation.register.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.prmto.auth_presentation.R
import com.prmto.auth_presentation.components.AuthTextField
import com.prmto.auth_presentation.register.event.RegisterEvent
import com.prmto.core_presentation.components.InstaButton
import com.prmto.core_presentation.util.TextFieldState

@Composable
internal fun VerifyPhoneNumberScreen(
    phoneNumber: String,
    verificationCodeValue: String,
    onEvent: (RegisterEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.enter_the_verification_code),
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = buildAnnotatedString {
                append(stringResource(R.string.we_have_sent_a_verification_code_to))
                append("+90 $phoneNumber")
            },
            textAlign = TextAlign.Center,
            lineHeight = TextUnit(value = 25f, type = TextUnitType.Sp),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.verification_code),
            textFieldState = TextFieldState(
                text = verificationCodeValue
            ),
            onValueChange = {
                onEvent(RegisterEvent.EnteredVerificationCode(it))
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        InstaButton(
            modifier = Modifier.fillMaxWidth(),
            buttonText = "Next",
            enabled = verificationCodeValue.isNotBlank(),
            onClick = {

            }
        )
    }
}