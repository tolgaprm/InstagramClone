package com.prmto.auth_presentation.components

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import com.prmto.core_domain.R
import com.prmto.core_domain.util.TextFieldError
import com.prmto.core_presentation.dummy_data_generator.passwordTextFieldState
import com.prmto.core_presentation.dummy_data_generator.textFieldState
import org.junit.Rule
import org.junit.Test
import com.prmto.auth_presentation.R as authPresentationR

class AuthTextFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun textIsEmpty_labelIsDisplayed() {
        composeTestRule.setContent {
            AuthTextField(
                label = "Email",
                textFieldState = textFieldState().copy(""),
                onValueChange = {}
            )
        }
        composeTestRule.onNodeWithText("Email")
            .assertIsDisplayed()
    }

    @Test
    fun textIsNotEmpty_labelIsNotDisplayed() {
        composeTestRule.setContent {
            AuthTextField(
                label = "Email",
                textFieldState = textFieldState(),
                onValueChange = {}
            )
        }
        composeTestRule.onNodeWithText("Email")
            .assertDoesNotExist()
    }

    @Test
    fun textFieldStateIsNotNull_whenErrorIsEmpty_showError() {
        composeTestRule.setContent {
            AuthTextField(
                label = "Email",
                textFieldState = textFieldState().copy(
                    text = "",
                    error = TextFieldError.Empty
                ),
                onValueChange = {}
            )
        }
        composeTestRule.onNodeWithText(context.getString(R.string.error_empty_field))
            .assertIsDisplayed()
    }

    @Test
    fun passwordFieldStateIsNotNull_whenErrorIsPasswordInvalid_showError() {
        composeTestRule.setContent {
            AuthTextField(
                label = "Password",
                passwordTextFieldState = passwordTextFieldState().copy(
                    text = "1234",
                    error = TextFieldError.PasswordInvalid
                ),
                onValueChange = {}
            )
        }
        composeTestRule.onNodeWithText(context.getString(R.string.password_invalid_field))
            .assertIsDisplayed()
    }

    @Test
    fun passwordFieldStateIsNotNull_whenPasswordVisibilityFalse_showPasswordVisibilityOffIcon() {
        composeTestRule.setContent {
            AuthTextField(
                label = "Password",
                passwordTextFieldState = passwordTextFieldState(),
                onValueChange = {}
            )
        }
        composeTestRule.onNodeWithContentDescription(context.getString(authPresentationR.string.show_password))
            .assertIsDisplayed()
    }

    @Test
    fun passwordFieldStateIsNotNull_whenPasswordVisibilityTrue_showPasswordVisibilityIcon() {
        composeTestRule.setContent {
            AuthTextField(
                label = "Password",
                passwordTextFieldState = passwordTextFieldState().copy(isPasswordVisible = true),
                onValueChange = {}
            )
        }
        composeTestRule.onNodeWithContentDescription(context.getString(authPresentationR.string.hide_password))
            .assertIsDisplayed()
    }
}