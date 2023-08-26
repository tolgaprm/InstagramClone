package com.prmto.auth_data.remote.datasource.auth

import com.google.firebase.auth.FirebaseAuth
import com.prmto.auth_data.remote.util.safeCallWithTryCatch
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
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

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): SimpleResource {
        return safeCallWithTryCatch {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(Unit)
        }
    }
}