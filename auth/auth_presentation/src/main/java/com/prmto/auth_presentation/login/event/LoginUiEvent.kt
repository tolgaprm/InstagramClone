package com.prmto.auth_presentation.login.event

sealed class LoginUiEvent {
    data class EnteredEmailOrUsername(val text: String) : LoginUiEvent()

    data class EnteredPassword(val password: String) : LoginUiEvent()

    data object OnForgotPasswordClicked : LoginUiEvent()

    data object OnLoginClicked : LoginUiEvent()
    data object TogglePasswordVisibility : LoginUiEvent()
}