package com.prmto.core_domain.model

data class UserDetail(
    val followingCount: Int = 0,
    val followersCount: Int = 0,
    val postsCount: Int = 0,
    val profilePictureUrl: String = "",
    val bio: String = "",
    val webSite: String = "",
)