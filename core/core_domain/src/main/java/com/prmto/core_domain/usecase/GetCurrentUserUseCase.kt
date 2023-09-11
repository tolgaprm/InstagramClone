package com.prmto.core_domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.prmto.core_domain.repository.auth.FirebaseAuthCoreRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authCoreRepository: FirebaseAuthCoreRepository
) {
    operator fun invoke(): FirebaseUser? {
        return authCoreRepository.currentUser()
    }
}