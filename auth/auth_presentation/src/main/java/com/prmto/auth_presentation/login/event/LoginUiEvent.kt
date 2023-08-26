package com.prmto.auth_presentation.login.event

sealed class LoginUiEvent {
    data class EnteredEmailOrUsername(val text: String) : LoginUiEvent()

    data class EnteredPassword(val password: String) : LoginUiEvent()

    object OnForgotPasswordClicked : LoginUiEvent()

    object OnLoginClicked : LoginUiEvent()
    object TogglePasswordVisibility : LoginUiEvent()
}