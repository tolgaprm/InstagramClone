package com.prmto.core_data.remote.datasource.auth

import com.google.firebase.auth.FirebaseUser

interface CoreAuthRemoteDataSource {
    fun signOut()
    fun currentUser(): FirebaseUser?
}