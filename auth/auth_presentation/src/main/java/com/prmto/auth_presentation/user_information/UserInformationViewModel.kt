package com.prmto.auth_presentation.user_information

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.prmto.auth_domain.repository.AuthRepository
import com.prmto.auth_domain.usecase.ValidatePasswordUseCase
import com.prmto.auth_domain.usecase.ValidateUsernameUseCase
import com.prmto.auth_presentation.user_information.event.UserInfoEvents
import com.prmto.auth_presentation.util.Constants
import com.prmto.core_domain.model.Statistics
import com.prmto.core_domain.model.UserData
import com.prmto.core_domain.model.UserDetail
import com.prmto.core_domain.repository.preferences.CoreUserPreferencesRepository
import com.prmto.core_domain.repository.user.FirebaseUserCoreRepository
import com.prmto.core_domain.usecase.CheckIfExistUserWithTheSameUsernameUseCase
import com.prmto.core_domain.util.Error
import com.prmto.core_domain.util.TextFieldError
import com.prmto.core_presentation.navigation.Screen
import com.prmto.core_presentation.util.CommonViewModel
import com.prmto.core_presentation.util.UiEvent
import com.prmto.core_presentation.util.isBlank
import com.prmto.core_presentation.util.isErrorNull
import com.prmto.core_presentation.util.updateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInformationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository,
    private val firebaseUserCoreRepository: FirebaseUserCoreRepository,
    private val coreUserPreferencesRepository: CoreUserPreferencesRepository,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val checkIfExistUserWithTheSameUsernameUseCase: CheckIfExistUserWithTheSameUsernameUseCase
) : CommonViewModel<UiEvent>() {
    private val _state = MutableStateFlow(UserInfoUiData())
    val state: StateFlow<UserInfoUiData> = _state.asStateFlow()

    init {
        savedStateHandle.get<String>(Constants.UserInfoEmailArgumentName)?.let { email ->
            _state.update { it.copy(email = email) }
        }
    }

    fun onEvent(event: UserInfoEvents) {
        when (event) {
            is UserInfoEvents.EnterFullName -> {
                updateFullName(fullName = event.fullName)
            }

            is UserInfoEvents.EnterUsername -> {
                updateUsername(username = event.username.trim())
            }

            is UserInfoEvents.EnterPassword -> {
                updatePassword(password = event.password)
            }

            is UserInfoEvents.TogglePasswordVisibility -> {
                _state.update {
                    it.copy(
                        passwordTextField = it.passwordTextField.updateState(
                            isPasswordVisible = !it.passwordTextField.isPasswordVisible
                        )
                    )
                }
            }

            is UserInfoEvents.Register -> {
                updateFullName(
                    fullName = state.value.fullNameTextField.text,
                    error = state.value.fullNameTextField.isBlank()
                )
                updateUsername(
                    username = state.value.usernameTextField.text,
                    error = validateUsernameUseCase(state.value.usernameTextField.text)
                )

                updatePassword(
                    password = state.value.passwordTextField.text,
                    error = validatePasswordUseCase(state.value.passwordTextField.text)
                )

                registerUser()
            }
        }
    }


    private fun registerUser() {
        if (state.value.fullNameTextField.isErrorNull() &&
            state.value.usernameTextField.isErrorNull() &&
            state.value.passwordTextField.isErrorNull()

        ) {
            _state.update { it.copy(isRegistering = true) }
            val userData = UserData(
                email = state.value.email.trim(),
                userDetail = UserDetail(
                    name = state.value.fullNameTextField.text.trim(),
                    username = state.value.usernameTextField.text.trim()
                ),
                statistics = Statistics()
            )

            checkIfExistAUserWithTheSameUsername(
                onCreateUser = {
                    createUserWithEmailAndPassword(userData = userData)
                }
            )
        }
    }

    private fun saveUserInfoToFirebase(
        userData: UserData,
        userUID: String
    ) {
        viewModelScope.launch {
            handleResourceWithCallbacks(
                resourceSupplier = {
                    firebaseUserCoreRepository.saveUser(
                        userData = userData,
                        userUid = userUID
                    )
                },
                onSuccessCallback = {
                    _state.update { it.copy(isRegistering = false) }
                    addConsumableViewEvent(UiEvent.Navigate(route = Screen.Home.route))
                },
                onErrorCallback = { uiText ->
                    _state.update { it.copy(isRegistering = false) }
                    addConsumableViewEvent(UiEvent.ShowMessage(uiText = uiText))
                }
            )
        }
    }

    private fun checkIfExistAUserWithTheSameUsername(
        onCreateUser: () -> Unit
    ) {
        viewModelScope.launch {
            handleResourceWithCallbacks(
                resourceSupplier = {
                    checkIfExistUserWithTheSameUsernameUseCase(
                        username = state.value.usernameTextField.text
                    )
                },
                onSuccessCallback = { isUserExist ->
                    if (isUserExist) {
                        _state.update {
                            it.copy(
                                isRegistering = false,
                                usernameTextField = it.usernameTextField.updateState(
                                    error = TextFieldError.UsernameAlreadyExists
                                )
                            )
                        }
                    } else {
                        onCreateUser()
                    }
                },
                onErrorCallback = { uiText ->
                    addConsumableViewEvent(UiEvent.ShowMessage(uiText = uiText))
                }
            )
        }
    }

    private fun createUserWithEmailAndPassword(userData: UserData) {
        viewModelScope.launch {
            handleResourceWithCallbacks(
                resourceSupplier = {
                    authRepository.createUserWithEmailAndPassword(
                        email = userData.email,
                        password = state.value.passwordTextField.text
                    )
                },
                onSuccessCallback = { userUid ->
                    saveUserInfoToFirebase(userData = userData, userUID = userUid)
                    saveUserDetailToPreferences(userDetail = userData.userDetail)
                },
                onErrorCallback = { uiText ->
                    _state.update { it.copy(isRegistering = false) }
                    addConsumableViewEvent(
                        UiEvent.ShowMessage(
                            uiText = uiText
                        )
                    )
                }
            )
        }
    }

    private fun saveUserDetailToPreferences(userDetail: UserDetail) {
        viewModelScope.launch {
            coreUserPreferencesRepository.saveUserDetail(userDetail = userDetail)
        }
    }

    private fun updateFullName(fullName: String, error: Error? = null) {
        _state.update {
            it.copy(
                fullNameTextField = state.value.fullNameTextField.updateState(
                    text = fullName, error = error
                )
            )
        }
    }

    private fun updateUsername(username: String, error: Error? = null) {
        _state.update {
            it.copy(
                usernameTextField = state.value.usernameTextField.updateState(
                    text = username, error = error
                )
            )
        }
    }

    private fun updatePassword(
        password: String, error: Error? = null
    ) {
        _state.update {
            it.copy(
                passwordTextField = state.value.passwordTextField.updateState(
                    text = password, error = error
                )
            )
        }
    }
}