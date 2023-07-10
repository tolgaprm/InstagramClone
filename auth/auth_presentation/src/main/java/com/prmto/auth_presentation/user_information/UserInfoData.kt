package com.prmto.auth_presentation.user_information

data class UserInfoData(
    val email: String = "",
    val phoneNumber: String = "",
    val nameAndSurname: String = "",
    val username: String = "",
    val password: String = ""
)
