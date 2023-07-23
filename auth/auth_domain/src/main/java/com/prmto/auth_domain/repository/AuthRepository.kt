package com.prmto.auth_domain.repository

interface AuthRepository {
    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: (uid: String) -> Unit,
        onError: (String) -> Unit
    )
}