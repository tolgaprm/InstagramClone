package com.prmto.auth_data.remote.datasource.auth

import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource

interface FirebaseAuthDataSource {
    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<String>

    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): SimpleResource
}