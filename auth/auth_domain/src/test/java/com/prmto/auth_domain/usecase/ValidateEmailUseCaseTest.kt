package com.prmto.auth_domain.usecase

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ValidateEmailUseCaseTest {

    private lateinit var validateEmailUseCase: ValidateEmailUseCase

    @Before
    fun setUp() {
        validateEmailUseCase = ValidateEmailUseCase()
    }

    @Test
    fun isValid_enterValidEmail_True() {
        val emailList = listOf(
            "email@example.com",
            "firstname.lastname@example.com",
            "email@subdomain.example.com",
            "irstname+lastname@example.com",
            "email@example.com",
            "1234567890@example.com",
            "email@example-one.com"
        )
        val result = emailList.map { email ->
            validateEmailUseCase(email = email)
        }
        assertThat(result).doesNotContain(false)
    }

    @Test
    fun isValid_enterInvalidEmail_False() {
        val emailList = listOf(
            "plainaddress",
            "#@%^%#$@#$@#.com",
            "@example.com",
            "Joe Smith < email @example.com >",
            "email.example.com",
            "email@example@example.com",
            ".email@example.com",
            "email.@example.com",
            "email..email@example.com",
            "あいうえお@example.com",
            "email@example.com(Joe Smith)",
            "email@example",
            "email@-example.com",
            "email@example..com",
            "Abc..123@example.com"
        )
        val result = emailList.map { email ->
            validateEmailUseCase(email = email)
        }

        assertThat(result).doesNotContain(true)
    }
}