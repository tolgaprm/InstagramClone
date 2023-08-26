package com.prmto.auth_presentation.fake_repository

import com.prmto.auth_domain.register.model.UserData
import com.prmto.auth_domain.repository.UserRepository
import com.prmto.auth_presentation.R
import com.prmto.auth_presentation.util.TestConstants
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.core_domain.constants.UiText
import kotlinx.coroutines.delay

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

    override suspend fun getUserEmailBySearchingUsername(username: String): Resource<String> {
        delay(1000)
        return if (TestConstants.ENTERED_USERNAME != username) {
            Resource.Error(UiText.StringResource(R.string.username_not_found))
        } else {
            Resource.Success(TestConstants.ENTERED_EMAIL)
        }
    }
}