package com.prmto.auth_presentation.util

import com.prmto.auth_domain.register.model.UserData

class TestConstants {
    companion object {
        const val ENTERED_EMAIL = "test@gmail.com"

        const val USER_EXISTS = "user exists"

        const val USER_UID = "user_uid"

        const val USER_EXISTS_USERNAME = "test_username"

        const val ENTERED_USERNAME = "username"

        const val ENTERED_VALID_PASSWORD = "123456"

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