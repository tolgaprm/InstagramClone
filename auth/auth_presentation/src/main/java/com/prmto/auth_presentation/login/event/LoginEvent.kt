package com.prmto.auth_presentation.login.event

sealed class LoginEvent {
    data class EnteredEmailOrUsername(val text: String) : LoginEvent()

    data class EnteredPassword(val password: String) : LoginEvent()

    data object OnForgotPasswordClicked : LoginEvent()

    data object OnLoginClicked : LoginEvent()
    data object TogglePasswordVisibility : LoginEvent()
}