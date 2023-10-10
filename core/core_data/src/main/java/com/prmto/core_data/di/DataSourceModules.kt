package com.prmto.core_data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.prmto.core_data.local.datasource.preferences.CoreUserPreferencesLocalDataSource
import com.prmto.core_data.local.datasource.preferences.CoreUserPreferencesLocalDataSourceImpl
import com.prmto.core_data.remote.datasource.auth.CoreAuthRemoteDataSource
import com.prmto.core_data.remote.datasource.auth.FirebaseCoreAuthRemoteDataSource
import com.prmto.core_data.remote.datasource.storage.FirebaseStorageDataSourceImpl
import com.prmto.core_data.remote.datasource.storage.StorageDataSource
import com.prmto.core_data.remote.datasource.user.FirebaseUserDataSource
import com.prmto.core_data.remote.datasource.user.FirebaseUserDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModules {
    @Provides
    @Singleton
    fun provideCoreAuthRemoteDataSource(
        firebaseAuth: FirebaseAuth
    ): CoreAuthRemoteDataSource {
        return FirebaseCoreAuthRemoteDataSource(firebaseAuth)
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
    fun provideCoreUserPreferencesLocalDataSource(
        dataStore: DataStore<Preferences>
    ): CoreUserPreferencesLocalDataSource {
        return CoreUserPreferencesLocalDataSourceImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideFirebaseStorageDataSource(
        firebaseStorage: FirebaseStorage
    ): StorageDataSource {
        return FirebaseStorageDataSourceImpl(firebaseStorage)
    }
}