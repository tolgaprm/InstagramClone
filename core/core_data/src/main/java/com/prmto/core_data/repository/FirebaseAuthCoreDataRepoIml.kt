package com.prmto.core_data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.prmto.core_domain.repository.FirebaseAuthCore
import javax.inject.Inject

class FirebaseAuthCoreDataRepoIml @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : FirebaseAuthCore {
    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun currentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}