package com.prmto.profile_data.di

import com.google.firebase.storage.FirebaseStorage
import com.prmto.profile_data.repository.ProfileRepositoryImpl
import com.prmto.profile_domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ProfileRepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideProfileRepository(
        firebaseStorage: FirebaseStorage
    ): ProfileRepository {
        return ProfileRepositoryImpl(firebaseStorage)
    }
}