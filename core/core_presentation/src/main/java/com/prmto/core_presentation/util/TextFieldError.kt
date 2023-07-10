package com.prmto.core_presentation.util

import com.prmto.core_presentation.R

abstract class Error(open val message: UiText? = null)


sealed class TextFieldError(override val message: UiText) : Error() {
    object Empty : TextFieldError(
        message = UiText.StringResource(R.string.error_empty_field)
    )

    object EmailInvalid : TextFieldError(
        message = UiText.StringResource(R.string.error_invalid_field)
    )

    object PasswordInvalid : TextFieldError(
        message = UiText.StringResource(R.string.password_invalid_field)
    )
}