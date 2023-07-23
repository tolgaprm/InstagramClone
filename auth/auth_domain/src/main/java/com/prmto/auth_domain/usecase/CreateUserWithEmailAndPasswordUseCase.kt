package com.prmto.auth_domain.usecase

import com.prmto.auth_domain.register.model.UserData
import com.prmto.auth_domain.repository.AuthRepository
import javax.inject.Inject

class CreateUserWithEmailAndPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(
        userData: UserData,
        onSuccess: (uid: String) -> Unit,
        onError: (String) -> Unit
    ) {
        authRepository.createUserWithEmailAndPassword(
            email = userData.email,
            password = userData.password,
            onSuccess = onSuccess,
            onError = onError
        )
    }
}