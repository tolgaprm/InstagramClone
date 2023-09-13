package com.prmto.core_domain.repository.user

import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.core_domain.model.UserData
import com.prmto.core_domain.model.UserDetail

interface FirebaseUserCoreRepository {
    suspend fun saveUser(
        userData: UserData,
        userUid: String
    ): SimpleResource

    suspend fun getUsers(): Resource<List<UserData>>

    suspend fun updateUserDetail(
        userDetail: UserDetail,
        userUid: String
    ): SimpleResource

    suspend fun getUserBySearchingUsername(
        username: String
    ): Resource<UserData>

    suspend fun getUserDataWithUserUid(userUid: String): Resource<UserData>

    suspend fun getUserDetailByEmail(email: String): Resource<UserDetail>
}