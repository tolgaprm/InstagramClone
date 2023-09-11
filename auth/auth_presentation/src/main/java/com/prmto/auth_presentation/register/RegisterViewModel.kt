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
import com.prmto.core_presentation.util.addNewUiEvent
import com.prmto.core_presentation.util.updateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiStateData())
    val uiState: StateFlow<RegisterUiStateData> = _uiState.asStateFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnClickTab -> {
                _uiState.update {
                    it.copy(
                        selectedTab = event.position,
                        phoneNumberTextField = TextFieldState(),
                        emailTextField = TextFieldState(),
                        isNextButtonEnabled = false
                    )
                }
            }

            is RegisterEvent.EnteredEmail -> {
                updateEmail(email = event.email.trim())
                isNextButtonEnabled()
            }

            is RegisterEvent.EnteredPhoneNumber -> {
                updatePhoneNumber(phoneNumber = event.phoneNumber)
                isNextButtonEnabled()
            }

            RegisterEvent.OnClickNext -> {
                viewModelScope.launch {
                    if (uiState.value.isPhoneNumberSelected()) {
                        return@launch
                    } else {
                        if (validateEmailUseCase(uiState.value.emailTextField.text)) {
                            _uiState.update {
                                it.copy(
                                    consumableViewEvents = uiState.value.consumableViewEvents.addNewUiEvent(
                                        UiEvent.Navigate(
                                            AuthNestedScreens.UserInformation.passEmail(uiState.value.emailTextField.text)
                                        )
                                    )
                                )
                            }
                        } else {
                            updateEmail(
                                email = uiState.value.emailTextField.text,
                                error = TextFieldError.EmailInvalid
                            )
                        }
                    }
                }
            }

            is RegisterEvent.EnteredVerificationCode -> {
                _uiState.update { it.copy(verificationCodeTextField = event.verificationCode) }
            }
        }
    }

    private fun updateEmail(email: String, error: TextFieldError? = null) {
        _uiState.update {
            it.copy(
                emailTextField = it.emailTextField.updateState(
                    text = email,
                    error = error
                )
            )
        }
    }

    private fun updatePhoneNumber(phoneNumber: String, error: TextFieldError? = null) {
        _uiState.update {
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
        val isEnabled = if (uiState.value.isPhoneNumberSelected()) {
            uiState.value.phoneNumberTextField.text.isNotBlank() && uiState.value.phoneNumberTextField.text.length == 10
        } else {
            uiState.value.emailTextField.text.isNotBlank()
        }
        _uiState.update { it.copy(isNextButtonEnabled = isEnabled) }
    }

    fun onEventConsumed() {
        _uiState.update {
            it.copy(
                consumableViewEvents = uiState.value.consumableViewEvents.drop(1)
            )
        }
    }
}