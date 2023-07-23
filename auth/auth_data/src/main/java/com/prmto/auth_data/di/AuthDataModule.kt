package com.prmto.auth_data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prmto.auth_data.repository.FirebaseAuthRepositoryImpl
import com.prmto.auth_data.repository.FirebaseUserRepositoryImpl
import com.prmto.auth_domain.repository.AuthRepository
import com.prmto.auth_domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AuthDataModule {

    @Provides
    @ViewModelScoped
    fun provideFirebaseRegisterRepository(
        auth: FirebaseAuth
    ): AuthRepository {
        return FirebaseAuthRepositoryImpl(
            auth
        )
    }

    @Provides
    @ViewModelScoped
    fun provideFirebaseUserRepositoryRepository(
        firestore: FirebaseFirestore
    ): UserRepository {
        return FirebaseUserRepositoryImpl(
            firestore
        )
    }
}