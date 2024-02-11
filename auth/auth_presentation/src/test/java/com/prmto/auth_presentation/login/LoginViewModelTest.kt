package com.prmto.auth_presentation.login

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prmto.auth_domain.usecase.ValidateEmailUseCase
import com.prmto.auth_domain.usecase.ValidatePasswordUseCase
import com.prmto.auth_presentation.fake_repository.FakeAuthRepository
import com.prmto.auth_presentation.login.event.LoginEvent
import com.prmto.core_domain.constants.UiText
import com.prmto.core_domain.repository.user.FirebaseUserCoreRepository
import com.prmto.core_domain.util.TextFieldError
import com.prmto.core_presentation.navigation.Screen
import com.prmto.core_presentation.util.UiEvent
import com.prmto.core_testing.fake_repository.preferences.CoreUserPreferencesRepositoryFake
import com.prmto.core_testing.fake_repository.user.FakeFirebaseUserCoreRepository
import com.prmto.core_testing.util.MainDispatcherRule
import com.prmto.core_testing.util.TestConstants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
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
    private lateinit var userRepository: FirebaseUserCoreRepository
    private lateinit var coreUserPreferencesRepository: CoreUserPreferencesRepositoryFake

    @Before
    fun setUp() {
        authRepository = FakeAuthRepository()
        userRepository = FakeFirebaseUserCoreRepository()
        coreUserPreferencesRepository = CoreUserPreferencesRepositoryFake()
        viewModel = LoginViewModel(
            validateEmailUseCase = ValidateEmailUseCase(),
            validatePasswordUseCase = ValidatePasswordUseCase(),
            authRepository = authRepository,
            firebaseUserCoreRepository = userRepository,
            coreUserPreferencesRepository = coreUserPreferencesRepository
        )
    }

    @Test
    fun `when event is entered email or username then update email or username`() = runTest {
        val emailOrUsername = TestConstants.ENTERED_EMAIL
        viewModel.onEvent(LoginEvent.EnteredEmailOrUsername(emailOrUsername))
        viewModel.uiState.test {
            assertThat(emailOrUsername).isEqualTo(awaitItem().emailOrUserNameTextFieldState.text)
        }
    }

    @Test
    fun `when event is entered password then update password`() = runTest {
        val password = "password"
        viewModel.onEvent(LoginEvent.EnteredPassword(password))
        viewModel.uiState.test {
            assertThat(password).isEqualTo(awaitItem().passwordTextFieldState.text)
        }
    }

    @Test
    fun `when event is toggle password visibility then update password visibility`() = runTest {
        viewModel.onEvent(LoginEvent.TogglePasswordVisibility)
        viewModel.uiState.test {
            assertThat(awaitItem().passwordTextFieldState.isPasswordVisible).isTrue()
        }
        viewModel.onEvent(LoginEvent.TogglePasswordVisibility)
        viewModel.uiState.test {
            assertThat(awaitItem().passwordTextFieldState.isPasswordVisible).isFalse()
        }
    }

    @Test
    fun `when event is login, emailOrUsername is empty, then Empty Error `() = runTest {
        viewModel.onEvent(LoginEvent.EnteredEmailOrUsername(""))
        viewModel.onEvent(LoginEvent.EnteredPassword("password"))
        viewModel.onEvent(LoginEvent.OnLoginClicked)
        viewModel.uiState.test {
            assertThat(awaitItem().emailOrUserNameTextFieldState.error).isEqualTo(TextFieldError.Empty)
        }
    }

    @Test
    fun `when event is login, password is empty, then Empty Error `() = runTest {
        viewModel.onEvent(LoginEvent.EnteredEmailOrUsername(TestConstants.ENTERED_EMAIL))
        viewModel.onEvent(LoginEvent.EnteredPassword(""))
        viewModel.onEvent(LoginEvent.OnLoginClicked)
        viewModel.uiState.test {
            assertThat(awaitItem().passwordTextFieldState.error).isEqualTo(TextFieldError.Empty)
        }
    }

    @Test
    fun `when event is login, password is invalid, then PasswordInvalid `() = runTest {
        viewModel.onEvent(LoginEvent.EnteredEmailOrUsername(TestConstants.ENTERED_EMAIL))
        viewModel.onEvent(LoginEvent.EnteredPassword("12345"))
        viewModel.onEvent(LoginEvent.OnLoginClicked)
        viewModel.uiState.test {
            assertThat(awaitItem().passwordTextFieldState.error).isEqualTo(TextFieldError.PasswordInvalid)
        }
    }

    @Test
    fun `when event is login, emailOrUsername is email, password is valid, then login success`() =
        runTest {
            val expectedUiEvent = UiEvent.Navigate(Screen.Home.route)
            viewModel.onEvent(LoginEvent.EnteredEmailOrUsername(TestConstants.ENTERED_EMAIL))
            viewModel.onEvent(LoginEvent.EnteredPassword(TestConstants.ENTERED_VALID_PASSWORD))
            viewModel.onEvent(LoginEvent.OnLoginClicked)
            viewModel.uiState.test {
                awaitItem()
                assertThat(awaitItem().isLoading).isTrue()
                advanceUntilIdle()
                val uiState = awaitItem()
                assertThat(uiState.isLoading).isFalse()
                assertThat(viewModel.consumableViewEvents.value.first()).isEqualTo(expectedUiEvent)
            }
        }

    @Test
    fun `when event is login, emailOrUsername is email, password is valid, signInWithEmailAndPassword return error, then login error`() =
        runTest {
            authRepository.setReturnError(true)
            val expectedUiEvent = UiEvent.ShowMessage(UiText.unknownError())
            viewModel.onEvent(LoginEvent.EnteredEmailOrUsername(TestConstants.ENTERED_EMAIL))
            viewModel.onEvent(LoginEvent.EnteredPassword(TestConstants.ENTERED_VALID_PASSWORD))
            viewModel.onEvent(LoginEvent.OnLoginClicked)
            viewModel.uiState.test {
                awaitItem()
                assertThat(awaitItem().isLoading).isTrue()
                advanceUntilIdle()
                val uiState = awaitItem()
                assertThat(uiState.isLoading).isFalse()
                assertThat(viewModel.consumableViewEvents.value.first()).isEqualTo(expectedUiEvent)
            }
        }

    @Test
    fun `when event is login, emailOrUsername is username, password is valid, then login success`() =
        runTest {
            val expectedUiEvent = UiEvent.Navigate(Screen.Home.route)
            viewModel.onEvent(LoginEvent.EnteredEmailOrUsername(TestConstants.ENTERED_USERNAME))
            viewModel.onEvent(LoginEvent.EnteredPassword(TestConstants.ENTERED_VALID_PASSWORD))
            viewModel.onEvent(LoginEvent.OnLoginClicked)
            viewModel.uiState.test {
                awaitItem()
                val state = awaitItem()
                // LoginWithUsername function is working
                assertThat(state.isLoading).isTrue()
                advanceUntilIdle()
                // loginWithEmail function is working
                assertThat(state.isLoading).isTrue()
                advanceTimeBy(TestConstants.DELAY_NETWORK)
                val uiState = awaitItem()
                assertThat(uiState.isLoading).isFalse()
                assertThat(viewModel.consumableViewEvents.value.first()).isEqualTo(expectedUiEvent)
            }
        }

    @Test
    fun `when event is login, emailOrUsername is username, username is not find, then login error `() =
        runTest {
            val exceptedUiEvent =
                UiEvent.ShowMessage(UiText.DynamicString(TestConstants.USERNAME_DOES_NOT_EXIST_ERROR))
            viewModel.onEvent(
                LoginEvent.EnteredEmailOrUsername(
                    TestConstants.ENTERED_USERNAME.plus(
                        "1"
                    )
                )
            )
            viewModel.onEvent(LoginEvent.EnteredPassword(TestConstants.ENTERED_VALID_PASSWORD))
            viewModel.onEvent(LoginEvent.OnLoginClicked)
            viewModel.uiState.test {
                awaitItem()
                assertThat(awaitItem().isLoading).isTrue()
                advanceUntilIdle()
                val uiState = awaitItem()
                assertThat(uiState.isLoading).isFalse()
                assertThat(viewModel.consumableViewEvents.value.first()).isEqualTo(exceptedUiEvent)
            }
        }
}