package com.prmto.auth_domain.di

import com.prmto.auth_domain.repository.AuthRepository
import com.prmto.auth_domain.usecase.CreateUserWithEmailAndPasswordUseCase
import com.prmto.auth_domain.usecase.RegisterUseCases
import com.prmto.auth_domain.usecase.UserInformationUseCases
import com.prmto.auth_domain.usecase.ValidateEmailUseCase
import com.prmto.auth_domain.usecase.ValidatePasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AuthDomainModule {

    @Provides
    @ViewModelScoped
    fun provideRegisterUseCases(): RegisterUseCases {
        return RegisterUseCases(
            ValidateEmailUseCase()
        )
    }

    @Provides
    @ViewModelScoped
    fun provideUserInformationUseCases(
        repository: AuthRepository
    ): UserInformationUseCases {
        return UserInformationUseCases(
            validatePassword = ValidatePasswordUseCase(),
            createUserWithEmailAndPassword = CreateUserWithEmailAndPasswordUseCase(repository)
        )
    }
}