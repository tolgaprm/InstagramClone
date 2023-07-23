package com.prmto.core_presentation.util

data class TextFieldState(
    val text: String = "",
    val error: com.prmto.core_domain.util.Error? = null
)

data class PasswordTextFieldState(
    val text: String = "",
    val error: com.prmto.core_domain.util.Error? = null,
    val isPasswordVisible: Boolean = false
)

fun TextFieldState.isBlank(): com.prmto.core_domain.util.TextFieldError? {
    return if (text.isBlank()) com.prmto.core_domain.util.TextFieldError.Empty else null
}

fun PasswordTextFieldState.isErrorNull(): Boolean {
    return error == null
}

fun TextFieldState.isErrorNull(): Boolean {
    return error == null
}

