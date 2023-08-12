package com.prmto.auth_presentation.register.event

import com.prmto.auth_presentation.register.SelectedTab

sealed class RegisterEvent {
    data class OnClickTab(val position: SelectedTab) : RegisterEvent()
    data class EnteredPhoneNumber(val phoneNumber: String) : RegisterEvent()
    data class EnteredEmail(val email: String) : RegisterEvent()
    data class EnteredVerificationCode(val verificationCode: String) : RegisterEvent()
    object OnClickNext : RegisterEvent()
}
