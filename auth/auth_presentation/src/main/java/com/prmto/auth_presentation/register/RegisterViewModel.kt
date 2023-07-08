package com.prmto.auth_presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.auth_presentation.navigation.RegisterScreen
import com.prmto.core_presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(RegisterData())
    val state: State<RegisterData> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
                viewModelScope.launch {
                    if (state.value.isPhoneNumberSelected()) {
                        _eventFlow.emit(
                            UiEvent.Navigate(RegisterScreen.VerifyPhoneNumber.route)
                        )
                    } else {

                    }
                }
            }

            is RegisterEvent.EnteredVerificationCode -> {
                _state.value =
                    _state.value.copy(verificationCodeTextField = event.verificationCode)
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
}