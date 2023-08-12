package com.prmto.auth_data.remote.datasource.auth

import com.prmto.core_domain.constants.Resource

interface FirebaseAuthDataSource {
    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<String>
}