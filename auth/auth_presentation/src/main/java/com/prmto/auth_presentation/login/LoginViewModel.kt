package com.prmto.auth_presentation.login

import androidx.lifecycle.ViewModel
import com.prmto.auth_presentation.login.event.LoginUiEvent
import com.prmto.core_domain.util.Error
import com.prmto.core_presentation.util.updatePasswordVisibility
import com.prmto.core_presentation.util.updateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

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

            LoginUiEvent.OnForgotPasswordClicked -> {}
            LoginUiEvent.OnLoginClicked -> {
                
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


    private fun updateEmailOrUsername(emailOrUsername: String, error: Error? = null) {
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
        password: String, error: Error? = null
    ) {
        _loginUiState.update {
            it.copy(
                passwordTextFieldState = loginUiState.value.passwordTextFieldState.updateState(
                    text = password, error = error
                )
            )
        }
    }
}