package com.prmto.auth_presentation.login

import androidx.lifecycle.viewModelScope
import com.prmto.auth_domain.repository.AuthRepository
import com.prmto.auth_domain.usecase.ValidateEmailUseCase
import com.prmto.auth_domain.usecase.ValidatePasswordUseCase
import com.prmto.auth_presentation.login.event.LoginEvent
import com.prmto.core_domain.model.UserDetail
import com.prmto.core_domain.repository.preferences.CoreUserPreferencesRepository
import com.prmto.core_domain.repository.user.FirebaseUserCoreRepository
import com.prmto.core_domain.util.Error
import com.prmto.core_domain.util.TextFieldError
import com.prmto.core_presentation.navigation.Screen
import com.prmto.core_presentation.util.CommonViewModel
import com.prmto.core_presentation.util.UiEvent
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
    private val firebaseUserCoreRepository: FirebaseUserCoreRepository,
    private val coreUserPreferencesRepository: CoreUserPreferencesRepository
) : CommonViewModel<UiEvent>() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmailOrUsername -> {
                updateEmailOrUsername(event.text)
            }

            is LoginEvent.EnteredPassword -> {
                updatePassword(event.password)
            }

            LoginEvent.OnForgotPasswordClicked -> {

            }

            LoginEvent.OnLoginClicked -> {
                login()
            }

            LoginEvent.TogglePasswordVisibility -> {
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

        val passwordErrorOrNull = validatePasswordUseCase(uiState.value.passwordTextFieldState.text)

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
            handleResourceWithCallbacks(
                resourceSupplier = {
                    authRepository.signInWithEmailAndPassword(
                        email = email,
                        password = uiState.value.passwordTextFieldState.text
                    )
                },
                onSuccessCallback = {
                    getUserDetailFromFirebaseAndUpdateToPreferences(email = email)
                },
                onErrorCallback = { uiText ->
                    _uiState.update { it.copy(isLoading = false) }
                    addConsumableViewEvent(UiEvent.ShowMessage(uiText = uiText))
                }
            )
        }
    }

    private fun loginWithUserName() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            handleResourceWithCallbacks(
                resourceSupplier = {
                    firebaseUserCoreRepository.getUserBySearchingUsername(
                        username = uiState.value.emailOrUserNameTextFieldState.text
                    )
                },
                onSuccessCallback = {
                    loginWithEmail(email = it.email)
                },
                onErrorCallback = { uiText ->
                    _uiState.update { it.copy(isLoading = false) }
                    addConsumableViewEvent(UiEvent.ShowMessage(uiText = uiText))
                }
            )
        }
    }

    private fun getUserDetailFromFirebaseAndUpdateToPreferences(email: String) {
        viewModelScope.launch {
            handleResourceWithCallbacks(
                resourceSupplier = {
                    firebaseUserCoreRepository.getUserDetailByEmail(email = email)
                },
                onSuccessCallback = { userDetail ->
                    saveUserDetailToPreferences(userDetail = userDetail)
                    _uiState.update { it.copy(isLoading = false) }
                    addConsumableViewEvent(UiEvent.Navigate(route = Screen.Home.route))
                },
                onErrorCallback = { uiText ->
                    _uiState.update { it.copy(isLoading = false) }
                    addConsumableViewEvent(UiEvent.ShowMessage(uiText = uiText))
                }
            )
        }
    }

    private fun saveUserDetailToPreferences(userDetail: UserDetail) {
        viewModelScope.launch {
            coreUserPreferencesRepository.saveUserDetail(userDetail = userDetail)
        }
    }

    private fun updateEmailOrUsername(
        emailOrUsername: String = uiState.value.emailOrUserNameTextFieldState.text,
        error: Error? = null
    ) {
        _uiState.update {
            it.copy(
                emailOrUserNameTextFieldState = uiState.value.emailOrUserNameTextFieldState.updateState(
                    text = emailOrUsername, error = error
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
}