package com.prmto.auth_presentation.login

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import com.prmto.auth_presentation.R
import org.junit.Rule
import org.junit.Test
import com.prmto.core_presentation.R as corePresentationR

class LoginScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun stateIsLoading_AuthButtonIsNotEnabled() {
        setLoginScreen(loginUiState = LoginUiState().copy(isLoading = true))
        composeTestRule.onNodeWithText(context.getString(R.string.login))
            .assertIsNotEnabled()
    }

    @Test
    fun stateIsNotLoading_AuthButtonIsEnabled() {
        setLoginScreen()
        composeTestRule.onNodeWithText(context.getString(R.string.login), useUnmergedTree = true)
            .assertIsEnabled()
        composeTestRule.onNodeWithContentDescription(
            context.getString(corePresentationR.string.progress_indicator),
            useUnmergedTree = true
        ).assertDoesNotExist()
    }

    @Test
    fun stateIsLoading_ShowCircularProgressIndicator() {
        setLoginScreen(loginUiState = LoginUiState().copy(isLoading = true))
        composeTestRule.onNodeWithContentDescription(
            context.getString(corePresentationR.string.progress_indicator),
            useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun isInstaLogoIsDisplay() {
        setLoginScreen()
        composeTestRule.onNodeWithContentDescription(context.getString(corePresentationR.string.logo))
            .assertIsDisplayed()
    }

    @Test
    fun forgotPasswordText_clickable() {
        setLoginScreen()
        composeTestRule.onNodeWithText(context.getString(R.string.forgot_password))
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun signUpText_clickable() {
        setLoginScreen()
        composeTestRule.onNodeWithText(context.getString(R.string.sign_up))
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    private fun setLoginScreen(loginUiState: LoginUiState = LoginUiState()) {
        composeTestRule.setContent {
            LoginScreen(
                loginUiState = loginUiState,
                onEvent = {},
                onNavigateToRegisterScreen = {}
            )
        }
    }
}