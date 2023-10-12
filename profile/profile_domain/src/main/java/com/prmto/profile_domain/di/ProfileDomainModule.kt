package com.prmto.profile_domain.di

import com.prmto.core_domain.repository.auth.FirebaseAuthCoreRepository
import com.prmto.core_domain.repository.user.FirebaseUserCoreRepository
import com.prmto.core_domain.usecase.CheckIfExistUserWithTheSameUsernameUseCase
import com.prmto.core_domain.usecase.GetCurrentUserUseCase
import com.prmto.profile_domain.usecase.EditProfileUseCases
import com.prmto.profile_domain.usecase.ValidateWebSiteUrlUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ProfileDomainModule {

    @ViewModelScoped
    @Provides
    fun provideEditProfileUseCase(
        firebaseAuthCoreRepository: FirebaseAuthCoreRepository,
        firebaseUserCoreRepository: FirebaseUserCoreRepository
    ): EditProfileUseCases {
        return EditProfileUseCases(
            getCurrentUser = GetCurrentUserUseCase(firebaseAuthCoreRepository),
            validateWebSiteUrl = ValidateWebSiteUrlUseCase(),
            checkIfExistUserWithTheSameUsername = CheckIfExistUserWithTheSameUsernameUseCase(
                firebaseUserCoreRepository
            )
        )
    }
}