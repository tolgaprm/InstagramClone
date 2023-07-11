package com.prmto.auth_domain.usecase

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ValidatePasswordUseCases {

    private lateinit var validatePassword: ValidatePasswordUseCase

    @Before
    fun setUp() {
        validatePassword = ValidatePasswordUseCase()
    }

    @Test
    fun `validatePassword, when password is empty, then return error`() {
        val password = ""
        val result = validatePassword(password = password)
        assertThat(result).isEqualTo(com.prmto.core_domain.util.TextFieldError.Empty)
    }

    @Test
    fun `validatePassword, when password is less than 6 characters, then return error`() {
        val password = "12345"
        val result = validatePassword(password = password)
        assertThat(result).isEqualTo(com.prmto.core_domain.util.TextFieldError.PasswordInvalid)
    }

    @Test
    fun `validatePassword, when password is validate, then return null`() {
        val password = "123456"
        val result = validatePassword(password = password)
        assertThat(result).isNull()
    }
}