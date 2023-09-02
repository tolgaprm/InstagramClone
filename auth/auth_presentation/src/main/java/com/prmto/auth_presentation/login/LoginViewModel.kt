package com.prmto.auth_presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.auth_domain.repository.AuthRepository
import com.prmto.auth_domain.usecase.ValidateEmailUseCase
import com.prmto.auth_domain.usecase.ValidatePasswordUseCase
import com.prmto.auth_presentation.login.event.LoginUiEvent
import com.prmto.core_domain.constants.onError
import com.prmto.core_domain.constants.onSuccess
import com.prmto.core_domain.repository.FirebaseUserCoreRepository
import com.prmto.core_domain.util.Error
import com.prmto.core_domain.util.TextFieldError
import com.prmto.core_presentation.navigation.Screen
import com.prmto.core_presentation.util.UiEvent
import com.prmto.core_presentation.util.addNewUiEvent
import com.prmto.core_presentation.util.updatePasswordVisibility
import com.prmto.core_presentation.util.updateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val authRepository: AuthRepository,
    private val firebaseUserCoreRepository: FirebaseUserCoreRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EnteredEmailOrUsername -> {
                updateEmailOrUsername(event.text)
            }

            is LoginUiEvent.EnteredPassword -> {
                updatePassword(event.password)
            }

            LoginUiEvent.OnForgotPasswordClicked -> {

            }

            LoginUiEvent.OnLoginClicked -> {
                login()
            }

            LoginUiEvent.TogglePasswordVisibility -> {
                _uiState.update {
                    it.copy(
                        passwordTextFieldState = uiState.value.passwordTextFieldState.updatePasswordVisibility()
                    )
                }
            }
        }
    }

    private fun login() {
        val emailOrUsernameText = uiState.value.emailOrUserNameTextFieldState.text
        if (emailOrUsernameText.isBlank()) {
            updateEmailOrUsername(
                error = TextFieldError.Empty
            )
            return
        }

        val isEmail = validateEmailUseCase(email = emailOrUsernameText)

        val passwordErrorOrNull =
            validatePasswordUseCase(uiState.value.passwordTextFieldState.text)

        if (passwordErrorOrNull != null) {
            updatePassword(error = passwordErrorOrNull)
            return
        }

        if (isEmail) {
            loginWithEmail(email = emailOrUsernameText)
        } else {
            loginWithUserName()
        }
    }

    private fun loginWithEmail(email: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            authRepository.signInWithEmailAndPassword(
                email,
                uiState.value.passwordTextFieldState.text
            ).onSuccess {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        consumableViewEvents = uiState.value.consumableViewEvents.addNewUiEvent(
                            UiEvent.Navigate(
                                Screen.Home.route
                            )
                        )
                    )
                }
            }.onError { uiText ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        consumableViewEvents = uiState.value.consumableViewEvents.addNewUiEvent(
                            UiEvent.ShowMessage(
                                uiText = uiText
                            )
                        )
                    )
                }
            }
        }
    }

    private fun loginWithUserName() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            firebaseUserCoreRepository.getUserEmailBySearchingUsername(
                username = uiState.value.emailOrUserNameTextFieldState.text
            ).onSuccess { email ->
                loginWithEmail(email = email)
            }.onError { uiText ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        consumableViewEvents = uiState.value.consumableViewEvents.addNewUiEvent(
                            UiEvent.ShowMessage(
                                uiText = uiText
                            )
                        )
                    )
                }
            }
        }
    }

    private fun updateEmailOrUsername(
        emailOrUsername: String = uiState.value.emailOrUserNameTextFieldState.text,
        error: Error? = null
    ) {
        _uiState.update {
            it.copy(
                emailOrUserNameTextFieldState = uiState.value.emailOrUserNameTextFieldState.updateState(
                    text = emailOrUsername,
                    error = error
                )
            )
        }
    }

    private fun updatePassword(
        password: String = uiState.value.passwordTextFieldState.text, error: Error? = null
    ) {
        _uiState.update {
            it.copy(
                passwordTextFieldState = uiState.value.passwordTextFieldState.updateState(
                    text = password, error = error
                )
            )
        }
    }

    fun onEventConsumed() {
        _uiState.update {
            it.copy(
                consumableViewEvents = uiState.value.consumableViewEvents.drop(1)
            )
        }
    }
}