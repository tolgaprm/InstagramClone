package com.prmto.core_domain.model

data class UserData(
    val email: String = "",
    val statistics: Statistics = Statistics(),
    val userDetail: UserDetail = UserDetail(),
)

data class Statistics(
    val followingCount: Int = 0,
    val followersCount: Int = 0,
    val postsCount: Int = 0
)

data class UserDetail(
    val bio: String = "",
    val name: String = "",
    val profilePictureUrl: String = "",
    val username: String = "",
    val webSite: String = "",
)