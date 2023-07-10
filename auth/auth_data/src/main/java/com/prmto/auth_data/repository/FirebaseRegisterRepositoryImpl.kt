package com.prmto.auth_data.repository

import com.google.firebase.auth.FirebaseAuth
import com.prmto.auth_domain.register.model.UserData
import com.prmto.auth_domain.register.repository.RegisterRepository

class FirebaseRegisterRepositoryImpl(
    private val auth: FirebaseAuth,
) : RegisterRepository {
    override fun createUserWithEmailAndPassword(
        userData: UserData,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(
            userData.email,
            userData.password
        )
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onError(exception.localizedMessage ?: "Unknown error")
            }
    }
}