package com.prmto.core_domain.usecase

import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.model.UserData
import com.prmto.core_domain.repository.user.FirebaseUserCoreRepository
import javax.inject.Inject

class GetUserBySearchingUsernameUseCase @Inject constructor(
    private val firebaseUserCoreRepository: FirebaseUserCoreRepository
) {
    suspend operator fun invoke(username: String): Resource<UserData> {
        return firebaseUserCoreRepository.getUserBySearchingUsername(username = username)
    }
}