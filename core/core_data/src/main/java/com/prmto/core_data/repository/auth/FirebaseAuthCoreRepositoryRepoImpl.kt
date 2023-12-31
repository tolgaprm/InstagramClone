package com.prmto.core_data.repository.auth

import com.google.firebase.auth.FirebaseUser
import com.prmto.core_data.remote.datasource.auth.CoreAuthRemoteDataSource
import com.prmto.core_domain.repository.auth.FirebaseAuthCoreRepository
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