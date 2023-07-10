package com.prmto.auth_data.di

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.prmto.auth_data.repository.FirebaseRegisterRepositoryImpl
import com.prmto.auth_domain.register.repository.RegisterRepository
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
    fun provideFirebaseAuth(
        firebaseApp: FirebaseApp
    ): FirebaseAuth {
        return FirebaseAuth.getInstance(firebaseApp)
    }

    @Provides
    @ViewModelScoped
    fun provideFirebaseRegisterRepository(
        auth: FirebaseAuth
    ): RegisterRepository {
        return FirebaseRegisterRepositoryImpl(
            auth
        )
    }
}