package com.prmto.auth_presentation.user_information

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.prmto.auth_domain.repository.AuthRepository
import com.prmto.auth_domain.usecase.ValidatePasswordUseCase
import com.prmto.auth_presentation.util.Constants.UserInfoEmailArgumentName
import com.prmto.auth_presentation.util.MainDispatcherRule
import com.prmto.core_domain.util.TextFieldError
import com.prmto.core_presentation.util.PasswordTextFieldState
import com.prmto.core_presentation.util.TextFieldState
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserInformationViewModelTest {

    private lateinit var viewModel: UserInformationViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var userInformationUseCases: UserInformationUseCases

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var mainCoroutineRule = MainDispatcherRule()

    @Before
    fun setUp() {
        savedStateHandle = mockk(relaxed = true)
        every {
            savedStateHandle.get<String>(UserInfoEmailArgumentName)
        } returns "test@gmail.com"

        userInformationUseCases = UserInformationUseCases(
            validatePassword = ValidatePasswordUseCase(),
            createUserWithEmailAndPassword = CreateUserWithEmailAndPasswordUseCase(
                mockk<AuthRepository>(relaxed = true),
            )
        )

        viewModel = UserInformationViewModel(
            savedStateHandle = savedStateHandle,
            userInformationUseCases = userInformationUseCases,
            userRepository = mockk(relaxed = true)
        )
    }

    @Test
    fun `initViewModel, updated email state correctly`() {
        assertThat(
            viewModel.state.value.email
        ).isEqualTo("test@gmail.com")
    }

    @Test
    fun `update state correctly, when event is EnterFullName`() {
        val fullName = "test"
        viewModel.onEvent(UserInfoEvents.EnterFullName(fullName = fullName))
        assertThat(
            viewModel.state.value.fullNameTextField
        ).isEqualTo(TextFieldState(text = fullName))
    }

    @Test
    fun `update state correctly, when event is EnterUsername`() {
        val username = "test"
        viewModel.onEvent(UserInfoEvents.EnterUsername(username = username))
        assertThat(
            viewModel.state.value.usernameTextField
        ).isEqualTo(TextFieldState(text = username))
    }

    @Test
    fun `update state correctly, when event is EnterPassword`() {
        val password = "123456"
        viewModel.onEvent(UserInfoEvents.EnterPassword(password = password))
        assertThat(
            viewModel.state.value.passwordTextField
        ).isEqualTo(PasswordTextFieldState(text = password))
    }

    @Test
    fun `togglePasswordVisibility should toggle the password visibility flag in the view model state`() {
        assertThat(viewModel.state.value.passwordTextField.isPasswordVisible)
            .isFalse()

        viewModel.onEvent(UserInfoEvents.TogglePasswordVisibility)
        assertThat(
            viewModel.state.value.passwordTextField.isPasswordVisible
        ).isTrue()
        viewModel.onEvent(UserInfoEvents.TogglePasswordVisibility)
        assertThat(
            viewModel.state.value.passwordTextField.isPasswordVisible
        ).isFalse()
    }


    @Test
    fun `register event should update fields correctly when all fields are valid`() {
        val fullName = "John Doe"
        val username = "johndoe"
        val password = "123456"

        viewModel.onEvent(UserInfoEvents.EnterFullName(fullName))
        viewModel.onEvent(UserInfoEvents.EnterUsername(username))
        viewModel.onEvent(UserInfoEvents.EnterPassword(password))
        viewModel.onEvent(UserInfoEvents.Register)

        val state = viewModel.state.value
        assertThat(state.fullNameTextField.error).isNull()
        assertThat(state.usernameTextField.error).isNull()
        assertThat(state.passwordTextField.error).isNull()
    }

    @Test
    fun `register event should show error when full name field is invalid`() {
        val fullName = ""
        val username = "johndoe"
        val password = "123456"

        viewModel.onEvent(UserInfoEvents.EnterFullName(fullName))
        viewModel.onEvent(UserInfoEvents.EnterUsername(username))
        viewModel.onEvent(UserInfoEvents.EnterPassword(password))
        viewModel.onEvent(UserInfoEvents.Register)

        val state = viewModel.state.value
        assertThat(state.fullNameTextField.error).isEqualTo(com.prmto.core_domain.util.TextFieldError.Empty)
    }

    @Test
    fun `register event should show error when username field is invalid`() {
        val fullName = "John Doe"
        val username = ""
        val password = "123456"

        viewModel.onEvent(UserInfoEvents.EnterFullName(fullName))
        viewModel.onEvent(UserInfoEvents.EnterUsername(username))
        viewModel.onEvent(UserInfoEvents.EnterPassword(password))
        viewModel.onEvent(UserInfoEvents.Register)

        val state = viewModel.state.value
        assertThat(state.usernameTextField.error).isEqualTo(com.prmto.core_domain.util.TextFieldError.Empty)
    }

    @Test
    fun `register event should show error when password field is invalid`() {
        val fullName = "John Doe"
        val username = "John"
        val password = "1234"

        viewModel.onEvent(UserInfoEvents.EnterFullName(fullName))
        viewModel.onEvent(UserInfoEvents.EnterUsername(username))
        viewModel.onEvent(UserInfoEvents.EnterPassword(password))
        viewModel.onEvent(UserInfoEvents.Register)

        val state = viewModel.state.value
        assertThat(state.passwordTextField.error).isEqualTo(TextFieldError.PasswordInvalid)
    }
}