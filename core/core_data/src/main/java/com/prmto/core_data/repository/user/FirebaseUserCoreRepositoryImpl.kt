package com.prmto.core_data.repository.user

import com.prmto.core_data.remote.datasource.user.FirebaseUserDataSource
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.core_domain.model.UserData
import com.prmto.core_domain.model.UserDetail
import com.prmto.core_domain.repository.user.FirebaseUserCoreRepository
import javax.inject.Inject

class FirebaseUserCoreRepositoryImpl @Inject constructor(
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

    override suspend fun updateUserDetail(userDetail: UserDetail, userUid: String): SimpleResource {
        return firebaseUserDataSource.updateUserDetail(
            userUid = userUid,
            userDetail = userDetail
        )
    }

    override suspend fun getUserBySearchingUsername(username: String): Resource<UserData> {
        return firebaseUserDataSource.getUserBySearchingUsername(username = username)
    }

    override suspend fun getUserDataWithUserUid(userUid: String): Resource<UserData> {
        return firebaseUserDataSource.getUserDataWithUserUid(userUid)
    }

    override suspend fun getUserDetailByEmail(email: String): Resource<UserDetail> {
        return firebaseUserDataSource.getUserDetailByEmail(email)
    }
}