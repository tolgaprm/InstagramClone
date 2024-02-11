package com.prmto.core_data.di

import com.prmto.core_data.local.datasource.preferences.CoreUserPreferencesLocalDataSource
import com.prmto.core_data.remote.datasource.auth.CoreAuthRemoteDataSource
import com.prmto.core_data.remote.datasource.user.FirebaseUserDataSource
import com.prmto.core_data.repository.auth.FirebaseAuthCoreRepositoryRepoImpl
import com.prmto.core_data.repository.preferences.CoreUserPreferencesRepositoryImpl
import com.prmto.core_data.repository.user.FirebaseUserCoreRepositoryImpl
import com.prmto.core_domain.repository.auth.FirebaseAuthCoreRepository
import com.prmto.core_domain.repository.preferences.CoreUserPreferencesRepository
import com.prmto.core_domain.repository.user.FirebaseUserCoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideFirebaseAuthCoreRepo(
        auth: CoreAuthRemoteDataSource
    ): FirebaseAuthCoreRepository {
        return FirebaseAuthCoreRepositoryRepoImpl(auth)
    }

    @Provides
    @Singleton
    fun provideFirebaseUserRepositoryRepository(
        userDataSource: FirebaseUserDataSource
    ): FirebaseUserCoreRepository {
        return FirebaseUserCoreRepositoryImpl(
            userDataSource
        )
    }

    @Provides
    @Singleton
    fun provideCoreUserPreferencesRepository(
        coreUserPreferencesLocalDataSource: CoreUserPreferencesLocalDataSource
    ): CoreUserPreferencesRepository {
        return CoreUserPreferencesRepositoryImpl(coreUserPreferencesLocalDataSource)
    }
}