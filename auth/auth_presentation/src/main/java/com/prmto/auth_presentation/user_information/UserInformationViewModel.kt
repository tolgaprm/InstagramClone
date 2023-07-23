package com.prmto.auth_presentation.user_information

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.auth_domain.register.model.UserData
import com.prmto.auth_domain.usecase.UserInformationUseCases
import com.prmto.auth_presentation.util.Constants
import com.prmto.core_domain.util.Error
import com.prmto.core_domain.util.TextFieldError
import com.prmto.core_domain.util.UiText
import com.prmto.core_presentation.navigation.Screen
import com.prmto.core_presentation.util.UiEvent
import com.prmto.core_presentation.util.isBlank
import com.prmto.core_presentation.util.isErrorNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInformationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userInformationUseCases: UserInformationUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(UserInfoData())
    val state: State<UserInfoData> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        savedStateHandle.get<String>(Constants.UserInfoEmailArgumentName)?.let { email ->
            _state.value = state.value.copy(
                email = email
            )
        }
    }

    fun onEvent(event: UserInfoEvents) {
        when (event) {
            is UserInfoEvents.EnterFullName -> {
                updateFullName(fullName = event.fullName)
            }

            is UserInfoEvents.EnterUsername -> {
                updateUsername(username = event.username)
            }

            is UserInfoEvents.EnterPassword -> {
                updatePassword(password = event.password)
            }

            is UserInfoEvents.TogglePasswordVisibility -> {
                _state.value = state.value.copy(
                    passwordTextField = state.value.passwordTextField.copy(
                        isPasswordVisible = !state.value.passwordTextField.isPasswordVisible
                    )
                )
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
                    error = userInformationUseCases.validatePassword(state.value.passwordTextField.text)
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
            _state.value = state.value.copy(isRegistering = true)
            val userData = UserData(
                email = state.value.email,
                fullName = state.value.fullNameTextField.text,
                username = state.value.usernameTextField.text,
                password = state.value.passwordTextField.text
            )

            checkIfExistAUserWithTheSameUsername {
                if (it) {
                    _state.value = state.value.copy(
                        isRegistering = false,
                        usernameTextField = state.value.usernameTextField.copy(
                            error = TextFieldError.UsernameAlreadyExists
                        )
                    )
                } else {
                    createUserWithEmailAndPassword(userData = userData)
                }
            }
        }
    }

    private fun saveUserInfoToDatabase(
        userData: UserData,
        userUID: String
    ) {
        userInformationUseCases.saveUserToDatabase(
            userData = userData,
            userUid = userUID,
            onSuccess = {
                emitUIEvent {
                    UiEvent.Navigate(
                        route = Screen.Home.route
                    )
                }
                _state.value = state.value.copy(isRegistering = false)
            },
            onError = { error ->
                emitUIEvent {
                    UiEvent.ShowMessage(
                        uiText = UiText.DynamicString(
                            value = error
                        )
                    )
                }
                _state.value = state.value.copy(isRegistering = false)
            }
        )
    }

    private fun checkIfExistAUserWithTheSameUsername(
        onCompleted: (Boolean) -> Unit
    ) {
        userInformationUseCases.getUsers(
            onSuccess = {
                val result = it.map {
                    it.username == state.value.usernameTextField.text
                }.find { true }

                onCompleted(result ?: false)
            },
            onError = { error ->
                emitUIEvent {
                    UiEvent.ShowMessage(
                        uiText = UiText.DynamicString(
                            value = error
                        )
                    )
                }
            }
        )
    }

    private fun createUserWithEmailAndPassword(userData: UserData) {
        userInformationUseCases.createUserWithEmailAndPassword(
            userData = userData,
            onSuccess = { userUID ->
                saveUserInfoToDatabase(
                    userData = userData,
                    userUID = userUID
                )
            },
            onError = { error ->
                emitUIEvent {
                    UiEvent.ShowMessage(
                        uiText = UiText.DynamicString(
                            value = error
                        )
                    )
                }
                _state.value = state.value.copy(isRegistering = false)

            }
        )
    }

    private fun updateFullName(fullName: String, error: Error? = null) {
        _state.value = state.value.copy(
            fullNameTextField = state.value.fullNameTextField.copy(
                text = fullName, error = error
            )
        )
    }

    private fun updateUsername(username: String, error: Error? = null) {
        _state.value = state.value.copy(
            usernameTextField = state.value.usernameTextField.copy(
                text = username, error = error
            )
        )
    }

    private fun updatePassword(
        password: String,
        error: Error? = null
    ) {
        _state.value = state.value.copy(
            passwordTextField = state.value.passwordTextField.copy(
                text = password, error = error
            )
        )
    }

    private fun emitUIEvent(func: (uiEvent: UiEvent) -> UiEvent) {
        viewModelScope.launch {
            _eventFlow.emit(func(UiEvent.Idle))
        }
    }
}