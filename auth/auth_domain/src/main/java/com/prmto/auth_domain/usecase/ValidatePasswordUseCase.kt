package com.prmto.auth_domain.usecase

import com.prmto.core_domain.util.Error
import com.prmto.core_domain.util.TextFieldError
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String): Error? {
        return when {
            password.isBlank() -> {
                TextFieldError.Empty
            }

            password.length < 6 -> {
                TextFieldError.PasswordInvalid
            }

            else -> {
                null
            }
        }
    }
}