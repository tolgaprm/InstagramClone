package com.prmto.auth_presentation.register

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.prmto.core_presentation.dummy_data_generator.textFieldState
import com.prmto.core_presentation.util.TextFieldState

data class RegisterUiStateData(
    val selectedTab: SelectedTab = SelectedTab.PHONE_NUMBER,
    val phoneNumberTextField: TextFieldState = TextFieldState(),
    val emailTextField: TextFieldState = TextFieldState(),
    val isNextButtonEnabled: Boolean = false,
    val verificationCodeTextField: String = ""
)

fun RegisterUiStateData.isPhoneNumberSelected(): Boolean {
    return selectedTab == SelectedTab.PHONE_NUMBER
}

enum class SelectedTab {
    PHONE_NUMBER,
    EMAIL
}

class RegisterUiStateDataPreviewProvider : PreviewParameterProvider<RegisterUiStateData> {
    override val values: Sequence<RegisterUiStateData>
        get() = sequenceOf(
            RegisterUiStateData(
                selectedTab = SelectedTab.EMAIL,
                emailTextField = textFieldState(),
                isNextButtonEnabled = true
            ),
            RegisterUiStateData(
                selectedTab = SelectedTab.PHONE_NUMBER,
                phoneNumberTextField = textFieldState().copy(
                    text = "55312345678"
                )
            )
        )
}