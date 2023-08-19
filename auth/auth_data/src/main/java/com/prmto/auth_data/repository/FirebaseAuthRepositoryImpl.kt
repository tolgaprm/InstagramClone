package com.prmto.auth_data.repository

import com.prmto.auth_data.remote.datasource.auth.FirebaseAuthDataSource
import com.prmto.auth_domain.repository.AuthRepository
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuthDataSource: FirebaseAuthDataSource
) : AuthRepository {
    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<String> {
        return firebaseAuthDataSource.createUserWithEmailAndPassword(email, password)
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): SimpleResource {
        return firebaseAuthDataSource.signInWithEmailAndPassword(email, password)
    }
}