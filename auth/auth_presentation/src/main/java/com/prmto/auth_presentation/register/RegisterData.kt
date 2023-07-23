package com.prmto.auth_presentation.register

import com.prmto.core_presentation.util.TextFieldState

data class RegisterData(
    val selectedTab: SelectedTab = SelectedTab.PHONE_NUMBER,
    val phoneNumberTextField: TextFieldState = TextFieldState(),
    val emailTextField: TextFieldState = TextFieldState(),
    val isNextButtonEnabled: Boolean = false,
    val verificationCodeTextField: String = ""
)


fun RegisterData.isPhoneNumberSelected(): Boolean {
    return selectedTab == SelectedTab.PHONE_NUMBER
}


enum class SelectedTab {
    PHONE_NUMBER,
    EMAIL
}