package com.prmto.core_data.remote.datasource.user

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.prmto.core_data.remote.util.FirebaseCollectionNames
import com.prmto.core_data.remote.util.FirebaseFieldName
import com.prmto.core_data.remote.util.safeCallWithTryCatch
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.core_domain.model.UserData
import com.prmto.core_domain.model.UserDetail
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseUserDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirebaseUserDataSource {
    override suspend fun saveUser(userData: UserData, userUid: String): SimpleResource {
        return safeCallWithTryCatch {
            firestore.collection(FirebaseCollectionNames.USERS_COLLECTION).document(userUid)
                .set(userData).await()
            Resource.Success(Unit)
        }
    }

    override suspend fun getUsers(): Resource<List<UserData>> {
        return safeCallWithTryCatch {
            val result =
                firestore.collection(FirebaseCollectionNames.USERS_COLLECTION).get().await()
            val users = result.toObjects(UserData::class.java)
            Resource.Success(users)
        }
    }

    override suspend fun getUserEmailBySearchingUsername(username: String): Resource<String> {
        return safeCallWithTryCatch {
            val result = firestore.collection(FirebaseCollectionNames.USERS_COLLECTION)
                .whereEqualTo(FirebaseFieldName.USERNAME_FIELD, username).get().await()
            val user = result.toObjects(UserData::class.java)
            Resource.Success(user[0].email)
        }
    }

    override suspend fun updateUserDetail(userUid: String, userDetail: UserDetail): SimpleResource {
        return safeCallWithTryCatch {
            firestore.collection(FirebaseCollectionNames.USERS_COLLECTION)
                .document(userUid).set(
                    userDetail,
                    SetOptions.mergeFields(FirebaseFieldName.USER_DETAIL_FIELD)
                ).await()
            Resource.Success(Unit)
        }
    }
}