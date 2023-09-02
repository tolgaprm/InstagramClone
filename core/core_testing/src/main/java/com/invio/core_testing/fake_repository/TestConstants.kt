package com.invio.core_testing.fake_repository

import com.prmto.core_domain.model.UserData

class TestConstants {
    companion object {
        const val ENTERED_EMAIL = "test@gmail.com"

        const val USER_EXISTS = "user exists"

        const val USER_UID = "user_uid"

        const val USER_EXISTS_USERNAME = "test_username"

        const val ENTERED_USERNAME = "username"

        const val ENTERED_VALID_PASSWORD = "123456"

        const val USERNAME_DOES_NOT_EXIST_ERROR = "Username does not exist"

        val listOfUserData = listOf(
            UserData(
                email = ENTERED_EMAIL,
                password = "123456",
                fullName = "",
                username = "test_username",
                emailPhoneNumber = ""
            )
        )
    }
}