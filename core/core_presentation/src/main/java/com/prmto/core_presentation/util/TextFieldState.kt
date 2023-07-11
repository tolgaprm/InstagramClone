package com.prmto.core_presentation.util

data class TextFieldState(
    val text: String = "",
    val error: Error? = null
)

data class PasswordTextFieldState(
    val text: String = "",
    val error: Error? = null,
    val isPasswordVisible: Boolean = false
)

fun TextFieldState.isBlank(): TextFieldError? {
    return if (text.isBlank()) TextFieldError.Empty else null
}

fun PasswordTextFieldState.isErrorNull(): Boolean {
    return error == null
}

fun TextFieldState.isErrorNull(): Boolean {
    return error == null
}

