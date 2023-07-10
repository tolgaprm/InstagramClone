package com.prmto.auth_domain.register.model

data class UserData(
    val email: String = "",
    val password: String = "",
    val fullName: String = "",
    val username: String = "",
    val emailPhoneNumber: String = ""
)
