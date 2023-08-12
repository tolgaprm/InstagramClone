package com.prmto.auth_domain.repository

import com.prmto.core_domain.constants.Resource

interface AuthRepository {
    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<String>
}