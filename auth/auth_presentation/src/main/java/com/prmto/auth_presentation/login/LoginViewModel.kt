package com.prmto.auth_presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.auth_domain.repository.AuthRepository
import com.prmto.auth_domain.repository.UserRepository
import com.prmto.auth_domain.usecase.ValidateEmailUseCase
import com.prmto.auth_domain.usecase.ValidatePasswordUseCase
import com.prmto.auth_presentation.R
import com.prmto.auth_presentation.login.event.LoginUiEvent
import com.prmto.core_domain.constants.UiText
import com.prmto.core_domain.constants.onError
import com.prmto.core_domain.constants.onSuccess
import com.prmto.core_domain.util.Error
import com.prmto.core_domain.util.TextFieldError
import com.prmto.core_presentation.navigation.Screen
import com.prmto.core_presentation.util.UiEvent
import com.prmto.core_presentation.util.updatePasswordVisibility
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
class LoginViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

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
                _loginUiState.update {
                    it.copy(
                        passwordTextFieldState = loginUiState.value.passwordTextFieldState.updatePasswordVisibility()
                    )
                }
            }
        }
    }

    private fun login() {
        val emailOrUsernameText = loginUiState.value.emailOrUserNameTextFieldState.text
        if (emailOrUsernameText.isBlank()) {
            updateEmailOrUsername(
                error = TextFieldError.Empty
            )
            return
        }

        val isEmail = validateEmailUseCase(email = emailOrUsernameText)

        val passwordErrorOrNull =
            validatePasswordUseCase(loginUiState.value.passwordTextFieldState.text)

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
            _loginUiState.update { it.copy(isLoading = true) }
            authRepository.signInWithEmailAndPassword(
                email,
                loginUiState.value.passwordTextFieldState.text
            ).onSuccess {
                addNewConsumableEvent(
                    UiEvent.Navigate(
                        Screen.Home.route
                    )
                )
            }.onError { uiText ->
                addNewConsumableEvent(
                    UiEvent.ShowMessage(
                        uiText = uiText
                    )
                )
                _loginUiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun loginWithUserName() {
        viewModelScope.launch {
            _loginUiState.update { it.copy(isLoading = true) }
            userRepository.getUserEmailBySearchingUsername(
                username = loginUiState.value.emailOrUserNameTextFieldState.text
            ).onSuccess { email ->
                loginWithEmail(email = email)
            }.onError {
                addNewConsumableEvent(
                    UiEvent.ShowMessage(
                        uiText = UiText.StringResource(R.string.username_not_found)
                    )
                )
                _loginUiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun updateEmailOrUsername(
        emailOrUsername: String = loginUiState.value.emailOrUserNameTextFieldState.text,
        error: Error? = null
    ) {
        _loginUiState.update {
            it.copy(
                emailOrUserNameTextFieldState = loginUiState.value.emailOrUserNameTextFieldState.updateState(
                    text = emailOrUsername,
                    error = error
                )
            )
        }
    }

    private fun updatePassword(
        password: String = loginUiState.value.passwordTextFieldState.text, error: Error? = null
    ) {
        _loginUiState.update {
            it.copy(
                passwordTextFieldState = loginUiState.value.passwordTextFieldState.updateState(
                    text = password, error = error
                )
            )
        }
    }

    fun onEventConsumed() {
        _loginUiState.update {
            it.copy(
                consumableViewEvents = loginUiState.value.consumableViewEvents.drop(1)
            )
        }
    }

    private fun addNewConsumableEvent(event: UiEvent) {
        _loginUiState.updateAndGet {
            it.copy(
                consumableViewEvents = loginUiState.value.consumableViewEvents + event
            )
        }
    }
}