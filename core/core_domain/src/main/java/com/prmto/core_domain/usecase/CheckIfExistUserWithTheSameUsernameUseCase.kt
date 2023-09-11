package com.prmto.core_domain.usecase

import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.repository.user.FirebaseUserCoreRepository
import javax.inject.Inject

class CheckIfExistUserWithTheSameUsernameUseCase @Inject constructor(
    private val firebaseUserCoreRepository: FirebaseUserCoreRepository
) {
    suspend operator fun invoke(username: String): Resource<Boolean> {
        return when (val response = firebaseUserCoreRepository.getUsers()) {
            is Resource.Success -> {
                val result = response.data.any { it.userDetail.username == username }
                Resource.Success(result)
            }

            is Resource.Error -> {
                Resource.Error(response.uiText)
            }
        }
    }
}