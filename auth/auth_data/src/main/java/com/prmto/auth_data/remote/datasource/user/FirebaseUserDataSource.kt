package com.prmto.auth_data.remote.datasource.user

import com.prmto.auth_domain.register.model.UserData
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource

interface FirebaseUserDataSource {
    suspend fun saveUser(userData: UserData, userUid: String): SimpleResource

    suspend fun getUsers(): Resource<List<UserData>>
}