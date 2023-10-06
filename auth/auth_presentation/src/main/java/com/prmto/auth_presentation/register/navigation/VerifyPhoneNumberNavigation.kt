package com.prmto.auth_presentation.register.navigation

import androidx.compose.runtime.Composable
import com.prmto.auth_presentation.register.event.RegisterEvent
import com.prmto.auth_presentation.register.screens.VerifyPhoneNumberScreen

@Composable
internal fun VerifyPhoneNumberRoute(
    phoneNumber: String,
    verificationCodeValue: String,
    onEvent: (RegisterEvent) -> Unit
) {
    VerifyPhoneNumberScreen(
        phoneNumber = phoneNumber,
        verificationCodeValue = verificationCodeValue,
        onEvent = onEvent
    )
}