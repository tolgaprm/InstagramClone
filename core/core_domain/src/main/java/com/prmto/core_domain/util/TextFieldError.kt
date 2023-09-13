package com.prmto.core_domain.util

import com.prmto.core_domain.R
import com.prmto.core_domain.constants.UiText

abstract class Error(open val message: UiText? = null)


sealed class TextFieldError(override val message: UiText) : Error() {
    data object Empty : TextFieldError(
        message = UiText.StringResource(R.string.error_empty_field)
    )

    data object EmailInvalid : TextFieldError(
        message = UiText.StringResource(R.string.error_invalid_field)
    )

    data object PasswordInvalid : TextFieldError(
        message = UiText.StringResource(R.string.password_invalid_field)
    )

    data object UsernameAlreadyExists : TextFieldError(
        message = UiText.StringResource(R.string.username_already_exists)
    )
}