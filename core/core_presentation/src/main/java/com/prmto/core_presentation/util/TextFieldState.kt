package com.prmto.core_presentation.util

import com.prmto.core_domain.util.Error
import com.prmto.core_domain.util.TextFieldError

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

fun TextFieldState.updateState(
    text: String = this.text,
    error: Error? = this.error
): TextFieldState {
    return copy(text = text, error = error)
}

fun PasswordTextFieldState.updateState(
    text: String = this.text,
    error: Error? = this.error,
    isPasswordVisible: Boolean = this.isPasswordVisible
): PasswordTextFieldState {
    return copy(text = text, error = error, isPasswordVisible = isPasswordVisible)
}