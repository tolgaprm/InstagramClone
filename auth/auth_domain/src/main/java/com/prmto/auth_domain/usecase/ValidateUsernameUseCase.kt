package com.prmto.auth_domain.usecase

import com.prmto.core_domain.util.Error
import com.prmto.core_domain.util.TextFieldError
import javax.inject.Inject

class ValidateUsernameUseCase @Inject constructor() {
    operator fun invoke(username: String): Error? {
        return when {
            username.isBlank() -> {
                TextFieldError.Empty
            }

            !isUsernameIsValid(username) -> {
                TextFieldError.UsernameInvalid
            }

            else -> {
                null
            }
        }
    }
}

private fun isUsernameIsValid(username: String): Boolean {
    return Regex("^[a-z0-9_]+$").matches(username)
}