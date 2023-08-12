package com.prmto.auth_presentation.register

import com.google.common.truth.Truth.assertThat
import com.prmto.auth_domain.usecase.ValidateEmailUseCase
import com.prmto.auth_presentation.navigation.AuthNestedScreens
import com.prmto.auth_presentation.register.event.RegisterEvent
import com.prmto.auth_presentation.util.MainDispatcherRule
import com.prmto.core_presentation.util.TextFieldState
import com.prmto.core_presentation.util.UiEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RegisterViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainDispatcherRule()

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        viewModel = RegisterViewModel(
            validateEmailUseCase = ValidateEmailUseCase()
        )
    }

    @Test
    fun `Update state correctly, When event is OnClickTab`() {
        val selectedTab = SelectedTab.values().random()
        val event = RegisterEvent.OnClickTab(position = selectedTab)

        viewModel.onEvent(event)

        assertThat(
            viewModel.state.value
        ).isEqualTo(
            RegisterUiStateData(
                selectedTab = selectedTab,
                phoneNumberTextField = TextFieldState(),
                emailTextField = TextFieldState(),
                isNextButtonEnabled = false
            )
        )
    }

    @Test
    fun `Update state correctly, When event is EnteredEmail`() {
        val email = "test@gmail.com"
        viewModel.onEvent(
            RegisterEvent.OnClickTab(position = SelectedTab.EMAIL)
        )
        viewModel.onEvent(RegisterEvent.EnteredEmail(email = email))

        assertThat(
            viewModel.state.value
        ).isEqualTo(
            RegisterUiStateData(
                selectedTab = SelectedTab.EMAIL,
                phoneNumberTextField = TextFieldState(),
                emailTextField = TextFieldState(text = email),
                isNextButtonEnabled = true
            )
        )
    }

    @Test
    fun `Update state correctly, When event is EnteredPhoneNumber`() {
        val phoneNumber = "5555555555"
        viewModel.onEvent(RegisterEvent.EnteredPhoneNumber(phoneNumber = phoneNumber))

        assertThat(
            viewModel.state.value
        ).isEqualTo(
            RegisterUiStateData(
                selectedTab = SelectedTab.PHONE_NUMBER,
                phoneNumberTextField = TextFieldState(
                    text = phoneNumber
                ),
                emailTextField = TextFieldState(),
                isNextButtonEnabled = true
            )
        )
    }

    @Test
    fun `given invalid email entered,when event is OnClickNext, then email error should be set to EmailInvalid`() {
        val invalidEmail = "test@gmail"
        viewModel.onEvent(RegisterEvent.OnClickTab(position = SelectedTab.EMAIL))
        viewModel.onEvent(RegisterEvent.EnteredEmail(email = invalidEmail))
        viewModel.onEvent(RegisterEvent.OnClickNext)

        assertThat(
            viewModel.state.value.emailTextField.error
        ).isEqualTo(
            com.prmto.core_domain.util.TextFieldError.EmailInvalid
        )
    }

    @Test
    fun `given valid email entered,when event is OnClickNext, then consumable event is Navigate to UserInfoScreen`() {
        val email = "test@gmail.com"
        viewModel.onEvent(RegisterEvent.OnClickTab(position = SelectedTab.EMAIL))
        viewModel.onEvent(RegisterEvent.EnteredEmail(email = email))
        viewModel.onEvent(RegisterEvent.OnClickNext)
        val state = viewModel.state.value
        assertThat(state.consumableViewEvents.first()).isEqualTo(
            UiEvent.Navigate(
                AuthNestedScreens.UserInformation.passEmail(email)
            )
        )
    }

    @Test
    fun `isNextButtonEnabled, When selectedTab is PHONE_NUMBER and phoneNumberTextField is not valid,False`() {
        viewModel.onEvent(RegisterEvent.OnClickTab(position = SelectedTab.PHONE_NUMBER))
        viewModel.onEvent(RegisterEvent.EnteredPhoneNumber(phoneNumber = "55555"))

        assertThat(
            viewModel.state.value.isNextButtonEnabled
        ).isFalse()
    }

    @Test
    fun `isNextButtonEnabled, When selectedTab is PHONE_NUMBER and phoneNumberTextField is valid,True`() {
        viewModel.onEvent(RegisterEvent.OnClickTab(position = SelectedTab.PHONE_NUMBER))
        viewModel.onEvent(RegisterEvent.EnteredPhoneNumber(phoneNumber = "5555555555"))

        assertThat(
            viewModel.state.value.isNextButtonEnabled
        ).isTrue()
    }

    @Test
    fun `isNextButtonEnabled, When selectedTab is EMAIL and email is  empty, False`() {
        viewModel.onEvent(RegisterEvent.OnClickTab(position = SelectedTab.EMAIL))
        viewModel.onEvent(RegisterEvent.EnteredEmail(email = ""))

        assertThat(
            viewModel.state.value.isNextButtonEnabled
        ).isFalse()
    }

    @Test
    fun `isNextButtonEnabled, When selectedTab is EMAIL and email is not empty, True`() {
        viewModel.onEvent(RegisterEvent.OnClickTab(position = SelectedTab.EMAIL))
        viewModel.onEvent(RegisterEvent.EnteredEmail(email = "test"))


        assertThat(
            viewModel.state.value.isNextButtonEnabled
        ).isTrue()
    }
}