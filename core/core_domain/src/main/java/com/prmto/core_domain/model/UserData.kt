package com.prmto.core_domain.model

data class UserData(
    val email: String = "",
    val password: String = "",
    val fullName: String = "",
    val username: String = "",
    val emailPhoneNumber: String = "",
    val userDetail: UserDetail = UserDetail()
)