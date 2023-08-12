package com.prmto.auth_presentation.register

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.auth_domain.usecase.ValidateEmailUseCase
import com.prmto.auth_presentation.navigation.AuthNestedScreens
import com.prmto.auth_presentation.register.event.RegisterEvent
import com.prmto.core_domain.util.TextFieldError
import com.prmto.core_presentation.util.TextFieldState
import com.prmto.core_presentation.util.UiEvent
import com.prmto.core_presentation.util.updateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterUiStateData())
    val state: StateFlow<RegisterUiStateData> = _state.asStateFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnClickTab -> {
                _state.update {
                    it.copy(
                        selectedTab = event.position,
                        phoneNumberTextField = TextFieldState(),
                        emailTextField = TextFieldState(),
                        isNextButtonEnabled = false
                    )
                }
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
                        if (validateEmailUseCase(state.value.emailTextField.text)) {
                            addNewConsumableEvent(
                                UiEvent.Navigate(
                                    AuthNestedScreens.UserInformation.passEmail(state.value.emailTextField.text)
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
                _state.update { it.copy(verificationCodeTextField = event.verificationCode) }
            }
        }
    }

    private fun updateEmail(email: String, error: TextFieldError? = null) {
        _state.update {
            it.copy(
                emailTextField = it.emailTextField.updateState(
                    text = email,
                    error = error
                )
            )
        }
    }

    private fun updatePhoneNumber(phoneNumber: String, error: TextFieldError? = null) {
        _state.update {
            it.copy(
                phoneNumberTextField = it.phoneNumberTextField.updateState(
                    text = phoneNumber,
                    error = error
                )
            )
        }
    }

    @VisibleForTesting
    fun isNextButtonEnabled() {
        val isEnabled = if (state.value.isPhoneNumberSelected()) {
            state.value.phoneNumberTextField.text.isNotBlank() && state.value.phoneNumberTextField.text.length == 10
        } else {
            state.value.emailTextField.text.isNotBlank()
        }
        _state.update { it.copy(isNextButtonEnabled = isEnabled) }
    }

    fun onEventConsumed() {
        _state.update {
            it.copy(
                consumableViewEvents = state.value.consumableViewEvents.drop(1)
            )
        }
    }

    private fun addNewConsumableEvent(event: UiEvent) {
        _state.updateAndGet {
            it.copy(
                consumableViewEvents = state.value.consumableViewEvents + event
            )
        }
    }
}