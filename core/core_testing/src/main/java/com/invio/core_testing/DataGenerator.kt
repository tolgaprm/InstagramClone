package com.invio.core_testing

import com.invio.core_testing.util.TestConstants
import com.prmto.core_domain.model.UserData
import com.prmto.core_domain.model.UserDetail

fun userData() = UserData(
    email = TestConstants.ENTERED_EMAIL,
    userDetail = userDetail()
)

fun userDetail(): UserDetail {
    return UserDetail(
        bio = "test_bio",
        name = "test_name",
        username = TestConstants.ENTERED_USERNAME
    )
}