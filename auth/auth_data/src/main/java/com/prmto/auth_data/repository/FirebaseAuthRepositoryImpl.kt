package com.prmto.auth_data.repository

import com.google.firebase.auth.FirebaseAuth
import com.prmto.auth_domain.repository.AuthRepository

class FirebaseAuthRepositoryImpl(
    private val auth: FirebaseAuth,
) : AuthRepository {
    override fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: (uid: String) -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(
            email,
            password
        )
            .addOnSuccessListener {
                onSuccess(it.user?.uid ?: "")
            }
            .addOnFailureListener { exception ->
                onError(exception.localizedMessage ?: "Unknown error")
            }
    }

}