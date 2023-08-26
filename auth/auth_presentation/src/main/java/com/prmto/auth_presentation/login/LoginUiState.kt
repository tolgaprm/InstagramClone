package com.prmto.auth_presentation.login

import com.prmto.core_presentation.util.PasswordTextFieldState
import com.prmto.core_presentation.util.TextFieldState
import com.prmto.core_presentation.util.UiEvent

data class LoginUiState(
    val emailOrUserNameTextFieldState: TextFieldState = TextFieldState(),
    val passwordTextFieldState: PasswordTextFieldState = PasswordTextFieldState(),
    val consumableViewEvents: List<UiEvent> = listOf(),
    val isLoading: Boolean = false
)
