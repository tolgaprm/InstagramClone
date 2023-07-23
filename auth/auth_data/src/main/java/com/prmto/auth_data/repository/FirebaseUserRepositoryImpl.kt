package com.prmto.auth_data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.prmto.auth_domain.register.model.UserData
import com.prmto.auth_domain.repository.UserRepository
import javax.inject.Inject

class FirebaseUserRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : UserRepository {
    override fun saveUser(
        userData: UserData,
        userUid: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        firebaseFirestore.collection("Users").document(userUid)
            .set(userData)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.localizedMessage ?: "Unknown error")
            }
    }
}