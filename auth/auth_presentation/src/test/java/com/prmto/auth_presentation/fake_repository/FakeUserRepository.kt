package com.prmto.auth_presentation.fake_repository

import com.prmto.auth_domain.register.model.UserData
import com.prmto.auth_domain.repository.UserRepository
import com.prmto.auth_presentation.util.TestConstants
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.core_domain.constants.UiText

class FakeUserRepository(
    private val isReturnError: Boolean = false
) : UserRepository {
    override suspend fun saveUser(userData: UserData, userUid: String): SimpleResource {
        return if (isReturnError) {
            Resource.Error(UiText.unknownError())
        } else {
            Resource.Success(Unit)
        }
    }

    override suspend fun getUsers(): Resource<List<UserData>> {
        return if (isReturnError) {
            Resource.Error(UiText.unknownError())
        } else {
            Resource.Success(TestConstants.listOfUserData)
        }
    }
}