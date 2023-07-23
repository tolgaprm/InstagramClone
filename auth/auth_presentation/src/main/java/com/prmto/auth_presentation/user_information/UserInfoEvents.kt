package com.prmto.auth_presentation.user_information

sealed class UserInfoEvents {
    data class EnterFullName(val fullName: String) : UserInfoEvents()
    data class EnterUsername(val username: String) : UserInfoEvents()
    data class EnterPassword(val password: String) : UserInfoEvents()
    object Register : UserInfoEvents()
    object TogglePasswordVisibility : UserInfoEvents()
}
