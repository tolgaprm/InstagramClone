package com.prmto.auth_presentation.login

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.prmto.core_domain.util.TextFieldError
import com.prmto.core_presentation.dummy_data_generator.passwordTextFieldState
import com.prmto.core_presentation.dummy_data_generator.textFieldState
import com.prmto.core_presentation.util.PasswordTextFieldState
import com.prmto.core_presentation.util.TextFieldState

data class LoginUiState(
    val emailOrUserNameTextFieldState: TextFieldState = TextFieldState(),
    val passwordTextFieldState: PasswordTextFieldState = PasswordTextFieldState(),
    val isLoading: Boolean = false
)

class LoginUiStatePreviewParameterProvider : PreviewParameterProvider<LoginUiState> {
    override val values: Sequence<LoginUiState>
        get() = sequenceOf(
            LoginUiState(
                emailOrUserNameTextFieldState = textFieldState(),
                passwordTextFieldState = passwordTextFieldState(),
                isLoading = false
            ),
            LoginUiState(
                emailOrUserNameTextFieldState = textFieldState().copy(
                    text = "",
                    error = TextFieldError.Empty
                ),
                passwordTextFieldState = passwordTextFieldState().copy(
                    text = "",
                    error = TextFieldError.PasswordInvalid
                ),
                isLoading = false
            )
        )
}

