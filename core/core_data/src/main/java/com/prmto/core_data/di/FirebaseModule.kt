package com.prmto.core_data.di

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(
        firebaseApp: FirebaseApp
    ): FirebaseAuth {
        return FirebaseAuth.getInstance(firebaseApp)
    }
}