package com.prmto.auth_domain.di

import com.prmto.auth_domain.usecase.RegisterUseCases
import com.prmto.auth_domain.usecase.ValidateEmailUseCase
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
}