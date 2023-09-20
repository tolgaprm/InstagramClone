package com.prmto.core_testing.util

import com.prmto.core_domain.model.UserData
import com.prmto.core_domain.model.UserDetail
import com.prmto.core_testing.userData
import com.prmto.core_testing.userDetail

class TestConstants {
    companion object {
        const val ENTERED_EMAIL = "test@gmail.com"

        const val USER_EXISTS = "user exists"

        const val USER_UID = "user_uid"

        const val ENTERED_USERNAME = "username"

        const val ENTERED_VALID_PASSWORD = "123456"

        const val USERNAME_DOES_NOT_EXIST_ERROR = "Username does not exist"

        const val DELAY_NETWORK = 1000L

        val listOfUserData = listOf(
            UserData(
                email = ENTERED_EMAIL,
                userDetail = UserDetail(
                    name = "test_username",
                    username = "test_username",
                )
            )
        )

        val listOfUserDataInFirebase = listOf(
            userData(),
            userData().copy(
                email = "test2@gmail.com",
                userDetail = userDetail().copy(username = "test2_username")
            ),
            userData().copy(
                email = "test3@gmail.com",
                userDetail = userDetail().copy(username = "test3_username")
            ),
        )
    }
}