package com.prmto.core_data.remote.datasource.user

import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.prmto.core_data.common.FirebaseCollectionNames
import com.prmto.core_data.common.FirebaseFieldName
import com.prmto.core_data.common.safeCallWithTryCatch
import com.prmto.core_domain.R
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.core_domain.constants.UiText
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
                .set(userData.copy(userUid = userUid)).await()
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

    override suspend fun updateUserDetail(userUid: String, userDetail: UserDetail): SimpleResource {
        return safeCallWithTryCatch {
            firestore.collection(FirebaseCollectionNames.USERS_COLLECTION)
                .document(userUid).set(
                    mapOf(FirebaseFieldName.USER_DETAIL_FIELD to userDetail),
                    SetOptions.mergeFields(
                        FirebaseFieldName.USER_DETAIL_FIELD
                    )
                ).await()
            Resource.Success(Unit)
        }
    }

    override suspend fun getUserBySearchingUsername(username: String): Resource<UserData> {
        return safeCallWithTryCatch {
            val response = firestore.collection(FirebaseCollectionNames.USERS_COLLECTION)
                .whereEqualTo(
                    FieldPath.of(
                        FirebaseFieldName.USER_DETAIL_FIELD,
                        FirebaseFieldName.USERNAME_FIELD
                    ),
                    username
                )
                .get()
                .await()
            val user = response.toObjects(UserData::class.java)
            if (user.isNotEmpty()) {
                Resource.Success(user.first())
            } else {
                Resource.Error(UiText.StringResource(R.string.username_not_found))
            }
        }
    }

    override suspend fun getUserDataWithUserUid(userUid: String): Resource<UserData> {
        return safeCallWithTryCatch {
            val response = firestore.collection(FirebaseCollectionNames.USERS_COLLECTION)
                .document(userUid)
                .get().await()
            val user = response.toObject(UserData::class.java)
            if (user != null) {
                Resource.Success(user)
            } else {
                Resource.Error(UiText.StringResource(R.string.user_not_found))
            }
        }
    }

    override suspend fun getUserDetailByEmail(email: String): Resource<UserDetail> {
        return safeCallWithTryCatch {
            val response = firestore.collection(FirebaseCollectionNames.USERS_COLLECTION)
                .whereEqualTo(
                    FirebaseFieldName.EMAIL_FIELD,
                    email
                )
                .get()
                .await()

            val user = response.toObjects(UserData::class.java)
            if (user.isNotEmpty()) {
                Resource.Success(user.first().userDetail)
            } else {
                Resource.Error(UiText.StringResource(R.string.user_not_found))
            }
        }
    }
}