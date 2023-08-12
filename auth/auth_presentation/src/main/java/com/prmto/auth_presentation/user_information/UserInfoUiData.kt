package com.prmto.auth_presentation.user_information

import com.prmto.core_presentation.util.PasswordTextFieldState
import com.prmto.core_presentation.util.TextFieldState
import com.prmto.core_presentation.util.UiEvent

data class UserInfoUiData(
    val isRegistering: Boolean = false,
    val email: String = "",
    val phoneNumber: String = "",
    val consumableViewEvents: List<UiEvent> = listOf(),
    val fullNameTextField: TextFieldState = TextFieldState(),
    val usernameTextField: TextFieldState = TextFieldState(),
    val passwordTextField: PasswordTextFieldState = PasswordTextFieldState()
)