package com.prmto.auth_presentation.register

import com.prmto.core_presentation.util.TextFieldState
import com.prmto.core_presentation.util.UiEvent

data class RegisterUiStateData(
    val selectedTab: SelectedTab = SelectedTab.PHONE_NUMBER,
    val consumableViewEvents: List<UiEvent> = listOf(),
    val phoneNumberTextField: TextFieldState = TextFieldState(),
    val emailTextField: TextFieldState = TextFieldState(),
    val isNextButtonEnabled: Boolean = false,
    val verificationCodeTextField: String = ""
)


fun RegisterUiStateData.isPhoneNumberSelected(): Boolean {
    return selectedTab == SelectedTab.PHONE_NUMBER
}


enum class SelectedTab {
    PHONE_NUMBER,
    EMAIL
}