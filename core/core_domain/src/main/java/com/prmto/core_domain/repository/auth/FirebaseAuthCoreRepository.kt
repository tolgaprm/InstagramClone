package com.prmto.core_domain.repository.auth

import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthCoreRepository {

    fun signOut()

    fun currentUser(): FirebaseUser?
}