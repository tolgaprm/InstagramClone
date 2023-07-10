package com.prmto.auth_presentation.user_information

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.prmto.auth_domain.register.model.UserData
import com.prmto.auth_domain.usecase.CreateUserWithEmailAndPasswordUseCase
import com.prmto.auth_presentation.util.Constants
import com.prmto.core_presentation.util.Error
import com.prmto.core_presentation.util.TextFieldError
import com.prmto.core_presentation.util.isBlank
import com.prmto.core_presentation.util.isErrorNull
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserInformationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createUserWithEmailAndPasswordUseCase: CreateUserWithEmailAndPasswordUseCase
) : ViewModel() {

    private val _state = mutableStateOf(UserInfoData())
    val state: State<UserInfoData> = _state

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
                    error = validatePassword(state.value.passwordTextField.text)
                )

                if (state.value.fullNameTextField.isErrorNull() &&
                    state.value.usernameTextField.isErrorNull() &&
                    state.value.passwordTextField.isErrorNull()
                ) {
                    createUserWithEmailAndPasswordUseCase(
                        userData = UserData(
                            email = state.value.email,
                            fullName = state.value.fullNameTextField.text,
                            username = state.value.usernameTextField.text,
                            password = state.value.passwordTextField.text
                        ),
                        onSuccess = {
                            Timber.d("User created successfully")
                        },
                        onError = {
                            Timber.d("Error creating user: $it")
                        }
                    )
                }
            }
        }
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

    private fun updatePassword(password: String, error: Error? = null) {
        _state.value = state.value.copy(
            passwordTextField = state.value.passwordTextField.copy(
                text = password, error = error
            )
        )
    }

    private fun validatePassword(password: String): Error? {
        return when {
            password.isBlank() -> {
                TextFieldError.Empty
            }

            password.length < 6 -> {
                TextFieldError.PasswordInvalid
            }

            else -> {
                null
            }
        }
    }
}