package com.prmto.auth_domain.usecase

import com.prmto.auth_domain.register.model.UserData
import com.prmto.auth_domain.repository.UserRepository
import javax.inject.Inject

class SaveUserToDatabaseUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(
        userData: UserData,
        userUid: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        userRepository.saveUser(
            userData = userData,
            userUid = userUid,
            onSuccess = onSuccess,
            onError = onError
        )
    }
}