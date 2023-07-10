package com.prmto.auth_presentation.register

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.auth_domain.usecase.RegisterUseCases
import com.prmto.auth_presentation.navigation.RegisterScreen
import com.prmto.core_presentation.util.TextFieldError
import com.prmto.core_presentation.util.TextFieldState
import com.prmto.core_presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCases: RegisterUseCases
) : ViewModel() {

    private val _state = mutableStateOf(RegisterData())
    val state: State<RegisterData> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnClickTab -> {
                _state.value = _state.value.copy(
                    selectedTab = event.position,
                    phoneNumberTextField = TextFieldState(),
                    emailTextField = TextFieldState(),
                    isNextButtonEnabled = false
                )
            }

            is RegisterEvent.EnteredEmail -> {
                updateEmail(email = event.email)
                isNextButtonEnabled()
            }

            is RegisterEvent.EnteredPhoneNumber -> {
                updatePhoneNumber(phoneNumber = event.phoneNumber)
                isNextButtonEnabled()
            }

            RegisterEvent.OnClickNext -> {
                viewModelScope.launch {
                    if (state.value.isPhoneNumberSelected()) {
                        return@launch
                    } else {
                        if (registerUseCases.validateEmail(state.value.emailTextField.text)) {
                            _eventFlow.emit(
                                UiEvent.Navigate(
                                    RegisterScreen.UserInformation.passEmail(state.value.emailTextField.text)
                                )
                            )
                        } else {
                            updateEmail(
                                email = state.value.emailTextField.text,
                                error = TextFieldError.EmailInvalid
                            )
                        }
                    }
                }
            }

            is RegisterEvent.EnteredVerificationCode -> {
                _state.value = state.value.copy(verificationCodeTextField = event.verificationCode)
            }
        }
    }

    private fun updateEmail(email: String, error: TextFieldError? = null) {
        _state.value = state.value.copy(
            emailTextField = state.value.emailTextField.copy(
                text = email,
                error = error
            )
        )
    }

    private fun updatePhoneNumber(phoneNumber: String, error: TextFieldError? = null) {
        _state.value = state.value.copy(
            phoneNumberTextField = state.value.phoneNumberTextField.copy(
                text = phoneNumber,
                error = error
            )
        )
    }

    @VisibleForTesting
    fun isNextButtonEnabled() {
        val isEnabled = if (state.value.isPhoneNumberSelected()) {
            state.value.phoneNumberTextField.text.isNotBlank() && state.value.phoneNumberTextField.text.length == 10
        } else {
            state.value.emailTextField.text.isNotBlank()
        }

        _state.value = _state.value.copy(
            isNextButtonEnabled = isEnabled
        )
    }
}