package com.prmto.core_domain.repository

import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthCoreRepository {

    fun signOut()

    fun currentUser(): FirebaseUser?
}