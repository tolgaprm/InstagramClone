package com.prmto.auth_domain.repository

import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource

interface AuthRepository {
    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<String>

    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): SimpleResource
}