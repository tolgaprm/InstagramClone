package com.prmto.profile_domain.usecase

import com.prmto.core_domain.usecase.CheckIfExistUserWithTheSameUsernameUseCase
import com.prmto.core_domain.usecase.GetCurrentUserUseCase

data class EditProfileUseCases(
    val getCurrentUser: GetCurrentUserUseCase,
    val validateWebSiteUrl: ValidateWebSiteUrlUseCase,
    val checkIfExistUserWithTheSameUsername: CheckIfExistUserWithTheSameUsernameUseCase
)
