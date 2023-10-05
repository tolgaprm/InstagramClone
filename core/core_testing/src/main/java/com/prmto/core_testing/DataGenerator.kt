package com.prmto.core_testing

import com.prmto.core_domain.model.UserData
import com.prmto.core_domain.model.UserDetail
import com.prmto.core_testing.util.TestConstants
import java.util.UUID

fun userData(
    userUid: String = UUID.randomUUID().toString()
) = UserData(
    email = TestConstants.ENTERED_EMAIL,
    userDetail = userDetail(),
    userUid = userUid
)

fun userDetail(): UserDetail {
    return UserDetail(
        bio = "test_bio",
        name = "test_name",
        username = TestConstants.ENTERED_USERNAME,
        webSite = "test_website"
    )
}