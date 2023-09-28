package com.prmto.auth_presentation.user_information

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.prmto.core_domain.util.TextFieldError
import com.prmto.core_presentation.dummy_data_generator.passwordTextFieldState
import com.prmto.core_presentation.dummy_data_generator.textFieldState
import com.prmto.core_presentation.util.PasswordTextFieldState
import com.prmto.core_presentation.util.TextFieldState

data class UserInfoUiData(
    val isRegistering: Boolean = false,
    val email: String = "",
    val phoneNumber: String = "",
    val fullNameTextField: TextFieldState = TextFieldState(),
    val usernameTextField: TextFieldState = TextFieldState(),
    val passwordTextField: PasswordTextFieldState = PasswordTextFieldState()
)

class UserInfoUiDataPreviewProvider : PreviewParameterProvider<UserInfoUiData> {
    override val values: Sequence<UserInfoUiData>
        get() = sequenceOf(
            UserInfoUiData(
                fullNameTextField = textFieldState().copy("Tolga"),
                usernameTextField = textFieldState().copy(text = "prmto"),
                passwordTextField = passwordTextFieldState()
            ),
            UserInfoUiData(
                fullNameTextField = textFieldState().copy("Tolga"),
                usernameTextField = textFieldState().copy(
                    text = "prmto",
                    error = TextFieldError.UsernameAlreadyExists
                ),
                passwordTextField = passwordTextFieldState().copy(
                    text = "12345",
                    error = TextFieldError.PasswordInvalid,
                    isPasswordVisible = true
                )
            )
        )
}