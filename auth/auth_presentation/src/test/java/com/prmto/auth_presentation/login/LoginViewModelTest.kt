package com.prmto.auth_presentation.login

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prmto.auth_domain.usecase.ValidateEmailUseCase
import com.prmto.auth_domain.usecase.ValidatePasswordUseCase
import com.prmto.auth_presentation.R
import com.prmto.auth_presentation.fake_repository.FakeAuthRepository
import com.prmto.auth_presentation.fake_repository.FakeUserRepository
import com.prmto.auth_presentation.login.event.LoginUiEvent
import com.prmto.auth_presentation.util.MainDispatcherRule
import com.prmto.auth_presentation.util.TestConstants
import com.prmto.core_domain.constants.UiText
import com.prmto.core_domain.util.TextFieldError
import com.prmto.core_presentation.navigation.Screen
import com.prmto.core_presentation.util.UiEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: LoginViewModel
    private lateinit var authRepository: FakeAuthRepository
    private lateinit var userRepository: FakeUserRepository

    @Before
    fun setUp() {
        authRepository = FakeAuthRepository()
        userRepository = FakeUserRepository()
        viewModel = LoginViewModel(
            validateEmailUseCase = ValidateEmailUseCase(),
            validatePasswordUseCase = ValidatePasswordUseCase(),
            authRepository = authRepository,
            userRepository = userRepository
        )
    }

    @Test
    fun `when event is entered email or username then update email or username`() = runTest {
        val emailOrUsername = TestConstants.ENTERED_EMAIL
        viewModel.onEvent(LoginUiEvent.EnteredEmailOrUsername(emailOrUsername))
        viewModel.uiState.test {
            assertThat(emailOrUsername).isEqualTo(awaitItem().emailOrUserNameTextFieldState.text)
        }
    }

    @Test
    fun `when event is entered password then update password`() = runTest {
        val password = "password"
        viewModel.onEvent(LoginUiEvent.EnteredPassword(password))
        viewModel.uiState.test {
            assertThat(password).isEqualTo(awaitItem().passwordTextFieldState.text)
        }
    }

    @Test
    fun `when event is toggle password visibility then update password visibility`() = runTest {
        viewModel.onEvent(LoginUiEvent.TogglePasswordVisibility)
        viewModel.uiState.test {
            assertThat(awaitItem().passwordTextFieldState.isPasswordVisible).isTrue()
        }
        viewModel.onEvent(LoginUiEvent.TogglePasswordVisibility)
        viewModel.uiState.test {
            assertThat(awaitItem().passwordTextFieldState.isPasswordVisible).isFalse()
        }
    }

    @Test
    fun `when event is login, emailOrUsername is empty, then Empty Error `() = runTest {
        viewModel.onEvent(LoginUiEvent.EnteredEmailOrUsername(""))
        viewModel.onEvent(LoginUiEvent.EnteredPassword("password"))
        viewModel.onEvent(LoginUiEvent.OnLoginClicked)
        viewModel.uiState.test {
            assertThat(awaitItem().emailOrUserNameTextFieldState.error).isEqualTo(TextFieldError.Empty)
        }
    }

    @Test
    fun `when event is login, password is empty, then Empty Error `() = runTest {
        viewModel.onEvent(LoginUiEvent.EnteredEmailOrUsername(TestConstants.ENTERED_EMAIL))
        viewModel.onEvent(LoginUiEvent.EnteredPassword(""))
        viewModel.onEvent(LoginUiEvent.OnLoginClicked)
        viewModel.uiState.test {
            assertThat(awaitItem().passwordTextFieldState.error).isEqualTo(TextFieldError.Empty)
        }
    }

    @Test
    fun `when event is login, password is invalid, then PasswordInvalid `() = runTest {
        viewModel.onEvent(LoginUiEvent.EnteredEmailOrUsername(TestConstants.ENTERED_EMAIL))
        viewModel.onEvent(LoginUiEvent.EnteredPassword("12345"))
        viewModel.onEvent(LoginUiEvent.OnLoginClicked)
        viewModel.uiState.test {
            assertThat(awaitItem().passwordTextFieldState.error).isEqualTo(TextFieldError.PasswordInvalid)
        }
    }

    @Test
    fun `when event is login, emailOrUsername is email, password is valid, then login success`() =
        runTest {
            val expectedUiEvent = UiEvent.Navigate(Screen.Home.route)
            viewModel.onEvent(LoginUiEvent.EnteredEmailOrUsername(TestConstants.ENTERED_EMAIL))
            viewModel.onEvent(LoginUiEvent.EnteredPassword(TestConstants.ENTERED_VALID_PASSWORD))
            viewModel.onEvent(LoginUiEvent.OnLoginClicked)
            viewModel.uiState.test {
                assertThat(awaitItem().isLoading).isTrue()
                advanceUntilIdle()
                val uiState = awaitItem()
                assertThat(uiState.isLoading).isFalse()
                assertThat(uiState.consumableViewEvents.first()).isEqualTo(expectedUiEvent)
            }
        }

    @Test
    fun `when event is login, emailOrUsername is email, password is valid, signInWithEmailAndPassword return error, then login error`() =
        runTest {
            authRepository.setReturnError(true)
            val expectedUiEvent = UiEvent.ShowMessage(UiText.unknownError())
            viewModel.onEvent(LoginUiEvent.EnteredEmailOrUsername(TestConstants.ENTERED_EMAIL))
            viewModel.onEvent(LoginUiEvent.EnteredPassword(TestConstants.ENTERED_VALID_PASSWORD))
            viewModel.onEvent(LoginUiEvent.OnLoginClicked)
            viewModel.uiState.test {
                assertThat(awaitItem().isLoading).isTrue()
                advanceUntilIdle()
                val uiState = awaitItem()
                assertThat(uiState.isLoading).isFalse()
                assertThat(uiState.consumableViewEvents.first()).isEqualTo(expectedUiEvent)
            }
        }

    @Test
    fun `when event is login, emailOrUsername is username, password is valid, then login success`() =
        runTest {
            val expectedUiEvent = UiEvent.Navigate(Screen.Home.route)
            viewModel.onEvent(LoginUiEvent.EnteredEmailOrUsername(TestConstants.ENTERED_USERNAME))
            viewModel.onEvent(LoginUiEvent.EnteredPassword(TestConstants.ENTERED_VALID_PASSWORD))
            viewModel.onEvent(LoginUiEvent.OnLoginClicked)
            viewModel.uiState.test {
                val state = awaitItem()
                // LoginWithUsername function is working
                assertThat(state.isLoading).isTrue()
                advanceUntilIdle()
                // loginWithEmail function is working
                assertThat(state.isLoading).isTrue()
                advanceUntilIdle()
                val uiState = awaitItem()
                assertThat(uiState.isLoading).isFalse()
                assertThat(uiState.consumableViewEvents.first()).isEqualTo(expectedUiEvent)
            }
        }

    @Test
    fun `when event is login, emailOrUsername is username, username is not find, then login error `() =
        runTest {
            val exceptedUiEvent =
                UiEvent.ShowMessage(UiText.StringResource(R.string.username_not_found))
            viewModel.onEvent(
                LoginUiEvent.EnteredEmailOrUsername(
                    TestConstants.ENTERED_USERNAME.plus(
                        "1"
                    )
                )
            )
            viewModel.onEvent(LoginUiEvent.EnteredPassword(TestConstants.ENTERED_VALID_PASSWORD))
            viewModel.onEvent(LoginUiEvent.OnLoginClicked)
            viewModel.uiState.test {
                assertThat(awaitItem().isLoading).isTrue()
                advanceUntilIdle()
                val uiState = awaitItem()
                assertThat(uiState.isLoading).isFalse()
                assertThat(uiState.consumableViewEvents.first()).isEqualTo(exceptedUiEvent)
            }
        }
}