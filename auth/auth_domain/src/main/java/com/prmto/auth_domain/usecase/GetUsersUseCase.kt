package com.prmto.auth_domain.usecase

import com.prmto.auth_domain.register.model.UserData
import com.prmto.auth_domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(
        onSuccess: (List<UserData>) -> Unit,
        onError: (String) -> Unit
    ) {
        userRepository.getUsers(
            onSuccess = onSuccess,
            onError = onError
        )
    }
}