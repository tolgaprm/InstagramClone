package com.invio.core_testing.fake_repository.user

import com.invio.core_testing.userData
import com.invio.core_testing.util.TestConstants
import com.invio.core_testing.util.TestConstants.Companion.USERNAME_DOES_NOT_EXIST_ERROR
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.core_domain.constants.UiText
import com.prmto.core_domain.model.UserData
import com.prmto.core_domain.model.UserDetail
import com.prmto.core_domain.repository.user.FirebaseUserCoreRepository
import kotlinx.coroutines.delay

class FakeFirebaseUserCoreRepository(
    private val isReturnError: Boolean = false
) : FirebaseUserCoreRepository {

    val userList = TestConstants.listOfUserDataInFirebase

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
            Resource.Success(userList)
        }
    }

    override suspend fun updateUserDetail(userDetail: UserDetail, userUid: String): SimpleResource {
        return Resource.Success(Unit)
    }

    override suspend fun getUserBySearchingUsername(username: String): Resource<UserData> {
        if (isReturnError) {
            return Resource.Error(UiText.unknownError())
        }
        delay(TestConstants.DELAY_NETWORK)
        val userData = userList.find { it.userDetail.username == username }
        return if (userData == null) {
            Resource.Error(UiText.DynamicString(USERNAME_DOES_NOT_EXIST_ERROR))
        } else {
            Resource.Success(userData)
        }
    }

    override suspend fun getUserDataWithUserUid(userUid: String): Resource<UserData> {
        if (isReturnError) {
            return Resource.Error(UiText.unknownError())
        }
        return if (userUid == TestConstants.USER_UID) {
            Resource.Success(userData())
        } else {
            Resource.Error(UiText.StringResource(com.prmto.core_domain.R.string.user_not_found))
        }
    }

    override suspend fun getUserDetailByEmail(email: String): Resource<UserDetail> {
        if (isReturnError) {
            return Resource.Error(UiText.unknownError())
        }
        val userData = userList.find { it.email == email }
        return if (userData != null) {
            Resource.Success(userData.userDetail)
        } else {
            Resource.Error(UiText.StringResource(com.prmto.core_domain.R.string.user_not_found))
        }
    }
}