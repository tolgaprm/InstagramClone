package com.prmto.core_data.remote

import com.google.firebase.auth.FirebaseUser

interface CoreAuthRemoteDataSource {
    fun signOut()
    fun currentUser(): FirebaseUser?
}