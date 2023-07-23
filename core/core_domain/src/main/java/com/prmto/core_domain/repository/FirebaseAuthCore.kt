package com.prmto.core_domain.repository

import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthCore {

    fun signOut()

    fun currentUser(): FirebaseUser?
}