package com.prmto.core_domain.repository.preferences

import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.core_domain.model.UserDetail

interface CoreUserPreferencesRepository {
    suspend fun saveUserDetail(userDetail: UserDetail): SimpleResource

    suspend fun getUserDetail(): Resource<UserDetail>

    suspend fun getProfilePictureUrl(): String?
}