package com.prmto.auth_presentation.user_information

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.auth_domain.repository.AuthRepository
import com.prmto.auth_domain.usecase.ValidatePasswordUseCase
import com.prmto.auth_presentation.user_information.event.UserInfoEvents
import com.prmto.auth_presentation.util.Constants
import com.prmto.core_domain.constants.onError
import com.prmto.core_domain.constants.onSuccess
import com.prmto.core_domain.model.Statistics
import com.prmto.core_domain.model.UserData
import com.prmto.core_domain.model.UserDetail
import com.prmto.core_domain.repository.preferences.CoreUserPreferencesRepository
import com.prmto.core_domain.repository.user.FirebaseUserCoreRepository
import com.prmto.core_domain.usecase.CheckIfExistUserWithTheSameUsernameUseCase
import com.prmto.core_domain.util.Error
import com.prmto.core_domain.util.TextFieldError
import com.prmto.core_presentation.navigation.Screen
import com.prmto.core_presentation.util.UiEvent
import com.prmto.core_presentation.util.isBlank
import com.prmto.core_presentation.util.isErrorNull
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
class UserInformationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository,
    private val firebaseUserCoreRepository: FirebaseUserCoreRepository,
    private val coreUserPreferencesRepository: CoreUserPreferencesRepository,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val checkIfExistUserWithTheSameUsernameUseCase: CheckIfExistUserWithTheSameUsernameUseCase
) : ViewModel() {
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
                    error = state.value.usernameTextField.isBlank()
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
        if (state.value.fullNameTextField.isErrorNull() && state.value.usernameTextField.isErrorNull() && state.value.passwordTextField.isErrorNull()) {
            _state.update { it.copy(isRegistering = true) }
            val userData = UserData(
                email = state.value.email,
                userDetail = UserDetail(
                    name = state.value.fullNameTextField.text,
                    username = state.value.usernameTextField.text
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
            firebaseUserCoreRepository.saveUser(
                userData = userData,
                userUid = userUID
            ).onSuccess {
                addNewConsumableEvent(
                    UiEvent.Navigate(
                        route = Screen.Home.route
                    )
                )
                _state.update { it.copy(isRegistering = false) }
            }.onError { uiText ->
                addNewConsumableEvent(
                    UiEvent.ShowMessage(
                        uiText = uiText
                    )
                )
                _state.update { it.copy(isRegistering = false) }
            }
        }
    }

    private fun checkIfExistAUserWithTheSameUsername(
        onCreateUser: () -> Unit
    ) {
        viewModelScope.launch {
            checkIfExistUserWithTheSameUsernameUseCase(username = state.value.usernameTextField.text)
                .onSuccess { isUserExist ->
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
                }
                .onError { uiText ->
                    addNewConsumableEvent(
                        UiEvent.ShowMessage(
                            uiText = uiText
                        )
                    )
                }
        }
    }

    private fun createUserWithEmailAndPassword(userData: UserData) {
        viewModelScope.launch {
            authRepository.createUserWithEmailAndPassword(
                email = userData.email,
                password = state.value.passwordTextField.text
            ).onSuccess { userUID ->
                saveUserInfoToFirebase(
                    userData = userData, userUID = userUID
                )
                saveUserDetailToPreferences(
                    userDetail = userData.userDetail.copy(
                        name = userData.userDetail.name.trim()
                    )
                )

            }.onError { uiText ->
                addNewConsumableEvent(
                    UiEvent.ShowMessage(
                        uiText = uiText
                    )
                )
                _state.update { it.copy(isRegistering = false) }
            }
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