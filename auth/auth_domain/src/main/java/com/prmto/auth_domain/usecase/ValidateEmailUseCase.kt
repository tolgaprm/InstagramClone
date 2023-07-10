package com.prmto.auth_domain.usecase

class ValidateEmailUseCase {

    operator fun invoke(email: String): Boolean {
        return Regex("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$").matches(email)
    }
}