package com.prmto.core_domain.model.dummyDataGenerator

import com.prmto.core_domain.model.Statistics
import com.prmto.core_domain.model.UserData
import com.prmto.core_domain.model.UserDetail

fun userData(
    statistics: Statistics = statistics(),
    userDetail: UserDetail = userDetail(),
): UserData = UserData(
    email = "test@gmail.com",
    userUid = "testUid",
    statistics = statistics,
    userDetail = userDetail
)

fun statistics(): Statistics = Statistics(
    followingCount = 50,
    followersCount = 50,
    postsCount = 10
)

fun userDetail(): UserDetail = UserDetail(
    bio = "test bio",
    name = "test name",
    profilePictureUrl = "",
    username = "test username",
    webSite = "test website"
)