package com.prmto.core_presentation.util

data class TextFieldState(
    val text: String = "",
    val error: Error? = null
)

fun TextFieldState.isBlank(): TextFieldError? {
    return if (text.isBlank()) TextFieldError.Empty else null
}

fun TextFieldState.isErrorNull(): Boolean {
    return error == null
}

