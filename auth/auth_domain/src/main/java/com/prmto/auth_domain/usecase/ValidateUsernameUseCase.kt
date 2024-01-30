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

/**
 * Function to check the validity of a username.
 *
 * This function checks whether the given username follows a specific pattern.
 * A valid username should only contain lowercase letters (a-z), digits (0-9), and underscores (_).
 * Additionally, the username cannot be empty and must contain at least one character.
 *
 * @param username The username to be checked.
 * @return True if the given username is valid, otherwise false.
 */
private fun isUsernameIsValid(username: String): Boolean {
    return Regex("^[a-z0-9_]+$").matches(username)
}