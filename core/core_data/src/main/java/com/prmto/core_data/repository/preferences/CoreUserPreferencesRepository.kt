package com.prmto.core_data.repository.preferences

import com.prmto.core_data.local.datasource.preferences.CoreUserPreferencesLocalDataSource
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.core_domain.model.UserDetail
import com.prmto.core_domain.repository.preferences.CoreUserPreferencesRepository
import javax.inject.Inject

class CoreUserPreferencesRepositoryImpl @Inject constructor(
    private val coreUserPreferencesLocalDataSource: CoreUserPreferencesLocalDataSource
) : CoreUserPreferencesRepository {
    override suspend fun saveUserDetail(userDetail: UserDetail): SimpleResource {
        return coreUserPreferencesLocalDataSource.saveUserDetail(userDetail = userDetail)
    }

    override suspend fun getUserDetail(): Resource<UserDetail> {
        return coreUserPreferencesLocalDataSource.getUserDetail()
    }

    override suspend fun getProfilePictureUrl(): String? {
        return coreUserPreferencesLocalDataSource.getProfilePictureUrl()
    }
}