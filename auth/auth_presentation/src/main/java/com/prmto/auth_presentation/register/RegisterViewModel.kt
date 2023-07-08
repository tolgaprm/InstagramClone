package com.prmto.auth_presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(RegisterData())
    val state: State<RegisterData> = _state


    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnClickTab -> {
                _state.value = _state.value.copy(
                    selectedTab = event.position,
                    phoneNumber = "",
                    email = ""
                )
            }

            is RegisterEvent.EnteredEmail -> {
                _state.value = _state.value.copy(email = event.email)
                isNextButtonEnabled()
            }

            is RegisterEvent.EnteredPhoneNumber -> {
                _state.value = _state.value.copy(phoneNumber = event.phoneNumber)
                isNextButtonEnabled()
            }

            RegisterEvent.OnClickNext -> {
                if (_state.value.isPhoneNumberSelected()) {
                    registerWithPhone()
                } else {
                    registerWithEmail()
                }
            }
        }
    }


    private fun isNextButtonEnabled() {
        val isEnabled = if (state.value.isPhoneNumberSelected()) {
            state.value.phoneNumber.isNotBlank() && state.value.phoneNumber.length == 10
        } else {
            state.value.email.isNotBlank()
        }

        _state.value = _state.value.copy(
            isNextButtonEnabled = isEnabled
        )
    }


    private fun registerWithPhone() {}

    private fun registerWithEmail() {}
}