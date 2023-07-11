package com.prmto.auth_presentation.user_information

import com.prmto.core_presentation.util.PasswordTextFieldState
import com.prmto.core_presentation.util.TextFieldState

data class UserInfoData(
    val email: String = "",
    val phoneNumber: String = "",
    val fullNameTextField: TextFieldState = TextFieldState(),
    val usernameTextField: TextFieldState = TextFieldState(),
    val passwordTextField: PasswordTextFieldState = PasswordTextFieldState()
)
