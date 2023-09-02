package com.invio.core_testing.fake_repository

import com.invio.core_testing.fake_repository.TestConstants.Companion.USERNAME_DOES_NOT_EXIST_ERROR
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.core_domain.constants.UiText
import com.prmto.core_domain.model.UserData
import com.prmto.core_domain.model.UserDetail
import com.prmto.core_domain.repository.FirebaseUserCoreRepository
import kotlinx.coroutines.delay

class FakeFirebaseUserCoreRepository(
    private val isReturnError: Boolean = false
) : FirebaseUserCoreRepository {
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
            Resource.Error(UiText.DynamicString(USERNAME_DOES_NOT_EXIST_ERROR))
        } else {
            Resource.Success(TestConstants.ENTERED_EMAIL)
        }
    }

    override suspend fun updateUserDetail(userDetail: UserDetail, userUid: String): SimpleResource {
        // TODO
        return Resource.Success(Unit)
    }
}