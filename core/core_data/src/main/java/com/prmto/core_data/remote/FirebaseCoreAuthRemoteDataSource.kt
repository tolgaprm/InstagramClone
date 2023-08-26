package com.prmto.core_data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

internal class FirebaseCoreAuthRemoteDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : CoreAuthRemoteDataSource {
    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun currentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}