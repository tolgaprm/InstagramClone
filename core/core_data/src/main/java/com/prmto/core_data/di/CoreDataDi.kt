package com.prmto.core_data.di

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prmto.core_data.remote.datasource.auth.CoreAuthRemoteDataSource
import com.prmto.core_data.remote.datasource.auth.FirebaseCoreAuthRemoteDataSource
import com.prmto.core_data.remote.datasource.user.FirebaseUserDataSource
import com.prmto.core_data.remote.datasource.user.FirebaseUserDataSourceImpl
import com.prmto.core_data.repository.auth.FirebaseAuthCoreRepositoryRepoImpl
import com.prmto.core_data.repository.user.FirebaseFirebaseUserCoreRepositoryImpl
import com.prmto.core_domain.repository.FirebaseAuthCoreRepository
import com.prmto.core_domain.repository.FirebaseUserCoreRepository
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

    @Provides
    @Singleton
    fun provideFirebaseUserDataSource(
        firestore: FirebaseFirestore
    ): FirebaseUserDataSource {
        return FirebaseUserDataSourceImpl(
            firestore
        )
    }

    @Provides
    @Singleton
    fun provideFirebaseUserRepositoryRepository(
        userDataSource: FirebaseUserDataSource
    ): FirebaseUserCoreRepository {
        return FirebaseFirebaseUserCoreRepositoryImpl(
            userDataSource
        )
    }
}