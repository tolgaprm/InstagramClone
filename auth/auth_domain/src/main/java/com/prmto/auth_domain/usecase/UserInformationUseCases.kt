package com.prmto.auth_domain.usecase

data class UserInformationUseCases(
    val validatePassword: ValidatePasswordUseCase,
    val createUserWithEmailAndPassword: CreateUserWithEmailAndPasswordUseCase,
    val getUsers: GetUsersUseCase,
    val saveUserToDatabase: SaveUserToDatabaseUseCase,
)
