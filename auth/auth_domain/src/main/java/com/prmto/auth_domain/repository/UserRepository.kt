package com.prmto.auth_domain.repository

import com.prmto.auth_domain.register.model.UserData
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource

interface UserRepository {
    suspend fun saveUser(
        userData: UserData,
        userUid: String
    ): SimpleResource

    suspend fun getUsers(): Resource<List<UserData>>

    suspend fun getUserEmailBySearchingUsername(username: String): Resource<String>
}