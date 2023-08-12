package com.prmto.auth_data.remote.datasource.auth

import com.google.firebase.auth.FirebaseAuth
import com.prmto.auth_data.remote.util.safeCallWithTryCatch
import com.prmto.core_domain.constants.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class FirebaseAuthDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : FirebaseAuthDataSource {
    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<String> {
        return safeCallWithTryCatch {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Resource.Success(authResult?.user?.uid ?: "")
        }
    }
}