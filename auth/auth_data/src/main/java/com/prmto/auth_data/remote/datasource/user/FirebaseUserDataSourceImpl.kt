package com.prmto.auth_data.remote.datasource.user

import com.google.firebase.firestore.FirebaseFirestore
import com.prmto.auth_data.remote.util.FirebaseCollectionNames
import com.prmto.auth_data.remote.util.safeCallWithTryCatch
import com.prmto.auth_domain.register.model.UserData
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
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
}