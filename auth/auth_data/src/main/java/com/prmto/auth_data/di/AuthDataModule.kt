package com.prmto.auth_data.di

import com.google.firebase.auth.FirebaseAuth
import com.prmto.auth_data.remote.datasource.auth.FirebaseAuthDataSource
import com.prmto.auth_data.remote.datasource.auth.FirebaseAuthDataSourceImpl
import com.prmto.auth_data.repository.FirebaseAuthRepositoryImpl
import com.prmto.auth_domain.repository.AuthRepository
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
    fun provideFirebaseAuthDataSource(
        auth: FirebaseAuth
    ): FirebaseAuthDataSource {
        return FirebaseAuthDataSourceImpl(
            auth
        )
    }

    @Provides
    @ViewModelScoped
    fun provideFirebaseAuthRepository(
        authRemoteDataSource: FirebaseAuthDataSource
    ): AuthRepository {
        return FirebaseAuthRepositoryImpl(
            authRemoteDataSource
        )
    }
}