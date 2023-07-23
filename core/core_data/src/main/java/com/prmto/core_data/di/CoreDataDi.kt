package com.prmto.core_data.di

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.prmto.core_data.repository.FirebaseAuthCoreDataRepoIml
import com.prmto.core_domain.repository.FirebaseAuthCore
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
    fun provideFirebaseAuthCoreRepo(
        auth: FirebaseAuth
    ): FirebaseAuthCore {
        return FirebaseAuthCoreDataRepoIml(auth)
    }
}