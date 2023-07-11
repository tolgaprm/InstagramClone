package com.prmto.auth_domain.usecase

data class UserInformationUseCases(
    val validatePassword: ValidatePasswordUseCase,
    val createUserWithEmailAndPassword: CreateUserWithEmailAndPasswordUseCase
)
