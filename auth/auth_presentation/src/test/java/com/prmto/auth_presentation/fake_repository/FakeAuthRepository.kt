package com.prmto.auth_presentation.fake_repository

import com.prmto.auth_domain.repository.AuthRepository
import com.prmto.auth_presentation.util.TestConstants
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.UiText

class FakeAuthRepository : AuthRepository {
    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<String> {
        return if (email == TestConstants.ENTERED_EMAIL) {
            Resource.Error(UiText.DynamicString(TestConstants.USER_EXISTS))
        } else {
            Resource.Success(TestConstants.USER_UID)
        }
    }
}