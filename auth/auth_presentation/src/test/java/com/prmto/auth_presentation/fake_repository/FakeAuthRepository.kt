package com.prmto.auth_presentation.fake_repository

import com.invio.core_testing.util.TestConstants
import com.prmto.auth_domain.repository.AuthRepository
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.core_domain.constants.UiText
import kotlinx.coroutines.delay

class FakeAuthRepository : AuthRepository {

    private var isReturnError = false

    fun setReturnError(value: Boolean) {
        isReturnError = value
    }

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

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): SimpleResource {
        delay(TestConstants.DELAY_NETWORK)
        return if (isReturnError) {
            Resource.Error(UiText.unknownError())
        } else {
            Resource.Success(Unit)
        }
    }
}