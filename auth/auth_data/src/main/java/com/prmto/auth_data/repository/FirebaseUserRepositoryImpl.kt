package com.prmto.auth_data.repository

import com.prmto.auth_data.remote.datasource.user.FirebaseUserDataSource
import com.prmto.auth_domain.register.model.UserData
import com.prmto.auth_domain.repository.UserRepository
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import javax.inject.Inject

class FirebaseUserRepositoryImpl @Inject constructor(
    private val firebaseUserDataSource: FirebaseUserDataSource
) : UserRepository {
    override suspend fun saveUser(
        userData: UserData,
        userUid: String
    ): SimpleResource {
        return firebaseUserDataSource.saveUser(userData = userData, userUid = userUid)
    }

    override suspend fun getUsers(): Resource<List<UserData>> {
        return firebaseUserDataSource.getUsers()
    }

    override suspend fun getUserEmailBySearchingUsername(username: String): Resource<String> {
        return firebaseUserDataSource.getUserEmailBySearchingUsername(username = username)
    }
}