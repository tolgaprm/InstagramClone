package com.prmto.auth_domain.usecase

import com.prmto.auth_domain.register.model.UserData
import com.prmto.auth_domain.register.repository.RegisterRepository
import javax.inject.Inject

class CreateUserWithEmailAndPasswordUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    operator fun invoke(
        userData: UserData,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        registerRepository.createUserWithEmailAndPassword(
            userData,
            onSuccess = onSuccess,
            onError = onError
        )
    }
}