package com.prmto.core_data.repository.user

import com.prmto.core_data.remote.datasource.user.FirebaseUserDataSource
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.core_domain.model.UserData
import com.prmto.core_domain.model.UserDetail
import com.prmto.core_domain.repository.FirebaseUserCoreRepository
import javax.inject.Inject

class FirebaseFirebaseUserCoreRepositoryImpl @Inject constructor(
    private val firebaseUserDataSource: FirebaseUserDataSource
) : FirebaseUserCoreRepository {
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

    override suspend fun updateUserDetail(userDetail: UserDetail, userUid: String): SimpleResource {
        return firebaseUserDataSource.updateUserDetail(
            userUid = userUid,
            userDetail = userDetail
        )
    }
}