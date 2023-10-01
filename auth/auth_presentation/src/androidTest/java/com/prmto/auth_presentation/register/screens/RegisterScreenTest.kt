package com.prmto.auth_presentation.register.screens

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import com.prmto.auth_presentation.R
import com.prmto.auth_presentation.register.RegisterUiStateData
import com.prmto.auth_presentation.register.SelectedTab
import com.prmto.core_presentation.dummy_data_generator.textFieldState
import org.junit.Rule
import org.junit.Test

class RegisterScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun selectedTabPhoneNumber_ShowNumber() {
        val phoneNumberText = "55312345678"
        setRegisterScreen(
            registerUiStateData = RegisterUiStateData(
                selectedTab = SelectedTab.PHONE_NUMBER,
                phoneNumberTextField = textFieldState().copy(text = phoneNumberText)
            )
        )
        composeTestRule.onNodeWithText(context.getString(R.string.phone_number)).assertIsSelected()
        composeTestRule.onNodeWithText(context.getString(R.string.email)).assertIsNotSelected()
        composeTestRule.onNodeWithText(phoneNumberText).assertIsDisplayed()
    }

    @Test
    fun selectedTabEmail_ShowEmail() {
        setRegisterScreen(
            registerUiStateData = RegisterUiStateData(
                selectedTab = SelectedTab.EMAIL,
                emailTextField = textFieldState()
            )
        )

        composeTestRule.onNodeWithText(context.getString(R.string.phone_number))
            .assertIsNotSelected()
        composeTestRule.onNodeWithText(context.getString(R.string.email)).assertIsSelected()
        composeTestRule.onNodeWithText("test@gmail.com").assertIsDisplayed()
    }

    @Test
    fun stateIsNextButtonEnabled_NextButtonIsEnabled() {
        setRegisterScreen(
            registerUiStateData = RegisterUiStateData(
                phoneNumberTextField = textFieldState(),
                isNextButtonEnabled = true
            )
        )

        composeTestRule.onNodeWithText(context.getString(R.string.next)).assertIsEnabled()
    }

    @Test
    fun stateIsNextButtonDisabled_NextButtonIsDisabled() {
        setRegisterScreen(
            registerUiStateData = RegisterUiStateData(
                phoneNumberTextField = textFieldState(),
                isNextButtonEnabled = false
            )
        )

        composeTestRule.onNodeWithText(context.getString(R.string.next)).assertIsNotEnabled()
    }

    @Test
    fun loginTextIsClickable() {
        setRegisterScreen()
        composeTestRule.onNodeWithText(context.getString(R.string.login)).assertHasClickAction()
    }

    private fun setRegisterScreen(registerUiStateData: RegisterUiStateData = RegisterUiStateData()) {
        composeTestRule.setContent {
            RegisterScreen(
                registerUiStateData = registerUiStateData,
                onNavigateToLogin = {},
                onEvent = {}
            )
        }
    }
}