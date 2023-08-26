package com.prmto.core_data.repository

import com.google.firebase.auth.FirebaseUser
import com.prmto.core_data.remote.CoreAuthRemoteDataSource
import com.prmto.core_domain.repository.FirebaseAuthCoreRepository
import javax.inject.Inject

class FirebaseAuthCoreRepositoryRepoImpl @Inject constructor(
    private val coreAuthRemoteDataSource: CoreAuthRemoteDataSource
) : FirebaseAuthCoreRepository {
    override fun signOut() {
        coreAuthRemoteDataSource.signOut()
    }

    override fun currentUser(): FirebaseUser? {
        return coreAuthRemoteDataSource.currentUser()
    }
}