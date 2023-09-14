package com.prmto.auth_presentation.login

import com.prmto.core_presentation.util.PasswordTextFieldState
import com.prmto.core_presentation.util.TextFieldState

data class LoginUiState(
    val emailOrUserNameTextFieldState: TextFieldState = TextFieldState(),
    val passwordTextFieldState: PasswordTextFieldState = PasswordTextFieldState(),
    val isLoading: Boolean = false
)