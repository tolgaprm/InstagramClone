package com.invio.core_testing.fake_repository.preferences

import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.core_domain.constants.UiText
import com.prmto.core_domain.model.UserDetail
import com.prmto.core_domain.repository.preferences.CoreUserPreferencesRepository

class CoreUserPreferencesRepositoryFake : CoreUserPreferencesRepository {
    var isReturnError = false
    var userDetailList = listOf<UserDetail>()

    override suspend fun saveUserDetail(userDetail: UserDetail): SimpleResource {
        return if (isReturnError) {
            Resource.Error(UiText.unknownError())
        } else {
            userDetailList = listOf(userDetail)
            Resource.Success(Unit)
        }
    }

    override suspend fun getUserDetail(): Resource<UserDetail> {
        return if (isReturnError) {
            Resource.Error(UiText.unknownError())
        } else {
            Resource.Success(userDetailList.first())
        }
    }

    override suspend fun getProfilePictureUrl(): String? {
        return userDetailList.first().profilePictureUrl
    }

    override suspend fun deleteUserDetail(): SimpleResource {
        return if (isReturnError) {
            Resource.Error(UiText.unknownError())
        } else {
            userDetailList = listOf()
            Resource.Success(Unit)
        }
    }
}