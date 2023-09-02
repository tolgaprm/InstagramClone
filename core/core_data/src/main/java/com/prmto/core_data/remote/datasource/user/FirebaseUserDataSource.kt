package com.prmto.core_data.remote.datasource.user

import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.core_domain.model.UserData
import com.prmto.core_domain.model.UserDetail

interface FirebaseUserDataSource {
    suspend fun saveUser(userData: UserData, userUid: String): SimpleResource

    suspend fun getUsers(): Resource<List<UserData>>

    suspend fun getUserEmailBySearchingUsername(username: String): Resource<String>

    suspend fun updateUserDetail(
        userUid: String,
        userDetail: UserDetail
    ): SimpleResource
}