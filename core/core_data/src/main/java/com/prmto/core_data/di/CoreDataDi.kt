package com.prmto.core_data.di

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.prmto.core_data.remote.CoreAuthRemoteDataSource
import com.prmto.core_data.remote.FirebaseCoreAuthRemoteDataSource
import com.prmto.core_data.repository.FirebaseAuthCoreRepositoryRepoImpl
import com.prmto.core_domain.repository.FirebaseAuthCoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreDataDi {

    @Provides
    @Singleton
    fun provideFirebaseAuth(
        firebaseApp: FirebaseApp
    ): FirebaseAuth {
        return FirebaseAuth.getInstance(firebaseApp)
    }

    @Provides
    @Singleton
    fun provideCoreAuthRemoteDataSource(
        firebaseAuth: FirebaseAuth
    ): CoreAuthRemoteDataSource {
        return FirebaseCoreAuthRemoteDataSource(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthCoreRepo(
        auth: CoreAuthRemoteDataSource
    ): FirebaseAuthCoreRepository {
        return FirebaseAuthCoreRepositoryRepoImpl(auth)
    }
}