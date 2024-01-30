package com.prmto.auth_domain.usecase

import com.google.common.truth.Truth.assertThat
import com.prmto.core_domain.util.TextFieldError
import org.junit.Before
import org.junit.Test

class ValidateUsernameUseCaseTest {

    private lateinit var validateUsername: ValidateUsernameUseCase

    @Before
    fun setUp() {
        validateUsername = ValidateUsernameUseCase()
    }

    @Test
    fun `validateUsername, when username is empty, then return TextFieldEmpty Error`() {
        val username = ""
        val result = validateUsername(username = username)
        assertThat(result).isEqualTo(TextFieldError.Empty)
    }

    @Test
    fun `validateUsername, when username contains uppercase letters, return TextFieldUsernameInvalid Error`() {
        val username = "Test"
        val result = validateUsername(username = username)
        assertThat(result).isEqualTo(TextFieldError.UsernameInvalid)
    }

    @Test
    fun `validateUsername, when username valid, return null`() {
        val username = "test_username99"
        val result = validateUsername(username = username)
        assertThat(result).isNull()
    }
}