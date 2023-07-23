package com.prmto.core_domain.util

import com.prmto.core_domain.R

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

    object UsernameAlreadyExists : TextFieldError(
        message = UiText.StringResource(R.string.username_already_exists)
    )
}