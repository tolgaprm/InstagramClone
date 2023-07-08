package com.prmto.auth_presentation.register

data class RegisterData(
    val selectedTab: SelectedTab = SelectedTab.PHONE_NUMBER,
    val phoneNumber: String = "",
    val email: String = "",
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