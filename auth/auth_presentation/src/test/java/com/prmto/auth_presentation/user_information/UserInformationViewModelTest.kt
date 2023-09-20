package com.prmto.auth_presentation.user_information

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prmto.auth_domain.repository.AuthRepository
import com.prmto.auth_domain.usecase.ValidatePasswordUseCase
import com.prmto.auth_domain.usecase.ValidateUsernameUseCase
import com.prmto.auth_presentation.fake_repository.FakeAuthRepository
import com.prmto.auth_presentation.user_information.event.UserInfoEvents
import com.prmto.auth_presentation.util.Constants.UserInfoEmailArgumentName
import com.prmto.core_domain.constants.UiText
import com.prmto.core_domain.usecase.CheckIfExistUserWithTheSameUsernameUseCase
import com.prmto.core_domain.util.TextFieldError
import com.prmto.core_presentation.navigation.Screen
import com.prmto.core_presentation.util.PasswordTextFieldState
import com.prmto.core_presentation.util.TextFieldState
import com.prmto.core_presentation.util.UiEvent
import com.prmto.core_testing.fake_repository.preferences.CoreUserPreferencesRepositoryFake
import com.prmto.core_testing.fake_repository.user.FakeFirebaseUserCoreRepository
import com.prmto.core_testing.util.MainDispatcherRule
import com.prmto.core_testing.util.TestConstants
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserInformationViewModelTest {
    private lateinit var viewModel: UserInformationViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var authRepository: AuthRepository
    private lateinit var firebaseUserCoreRepository: FakeFirebaseUserCoreRepository
    private lateinit var coreUserPreferencesRepository: CoreUserPreferencesRepositoryFake

    @get:Rule
    var mainCoroutineRule = MainDispatcherRule()

    @Before
    fun setUp() {
        savedStateHandle = mockk(relaxed = true)
        every {
            savedStateHandle.get<String>(UserInfoEmailArgumentName)
        } returns TestConstants.ENTERED_EMAIL

        authRepository = FakeAuthRepository()
        coreUserPreferencesRepository = CoreUserPreferencesRepositoryFake()

        firebaseUserCoreRepository = FakeFirebaseUserCoreRepository()

        viewModel = UserInformationViewModel(
            savedStateHandle = savedStateHandle,
            firebaseUserCoreRepository = firebaseUserCoreRepository,
            authRepository = authRepository,
            validatePasswordUseCase = ValidatePasswordUseCase(),
            checkIfExistUserWithTheSameUsernameUseCase = CheckIfExistUserWithTheSameUsernameUseCase(
                firebaseUserCoreRepository
            ),
            coreUserPreferencesRepository = coreUserPreferencesRepository,
            validateUsernameUseCase = ValidateUsernameUseCase()
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
        assertThat(state.fullNameTextField.error).isEqualTo(TextFieldError.Empty)
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
        assertThat(state.usernameTextField.error).isEqualTo(TextFieldError.Empty)
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

    @Test
    fun `when entered valid all field and username has exists in the database, then usernameTextField update UsernameAlreadyExists`() =
        runTest {
            val fullName = "John Doe"
            val username = TestConstants.ENTERED_USERNAME
            val password = "123456"

            viewModel.onEvent(UserInfoEvents.EnterFullName(fullName))
            viewModel.onEvent(UserInfoEvents.EnterUsername(username))
            viewModel.onEvent(UserInfoEvents.EnterPassword(password))
            viewModel.onEvent(UserInfoEvents.Register)

            viewModel.state.test {
                val state = awaitItem()
                assertThat(state.usernameTextField.error).isEqualTo(TextFieldError.UsernameAlreadyExists)
            }
        }

    @Test
    fun `when entered valid all field, email is already exists, uiEvent is ShowMessage `() =
        runTest {
            val fullName = "John Doe"
            val username = "john"
            val password = "123456"

            viewModel.onEvent(UserInfoEvents.EnterFullName(fullName))
            viewModel.onEvent(UserInfoEvents.EnterUsername(username))
            viewModel.onEvent(UserInfoEvents.EnterPassword(password))
            viewModel.onEvent(UserInfoEvents.Register)

            viewModel.state.test {
                val state = awaitItem()
                assertThat(viewModel.consumableViewEvents.value.first()).isEqualTo(
                    UiEvent.ShowMessage(
                        UiText.DynamicString(TestConstants.USER_EXISTS)
                    )
                )
            }
        }

    @Test
    fun `when entered valid all field, user is created and registered, event is UiNavigate to HomeScreen `() =
        runTest {
            val savedStateHandle: SavedStateHandle = mockk(relaxed = true)
            every {
                savedStateHandle.get<String>(UserInfoEmailArgumentName)
            } returns "john@gmail.com"

            passNewSavedStateHandleWithNewEmail(savedStateHandle)

            val fullName = "John Doe"
            val username = "john"
            val password = "123456"

            viewModel.onEvent(UserInfoEvents.EnterFullName(fullName))
            viewModel.onEvent(UserInfoEvents.EnterUsername(username))
            viewModel.onEvent(UserInfoEvents.EnterPassword(password))
            viewModel.onEvent(UserInfoEvents.Register)

            viewModel.state.test {
                val state = awaitItem()
                assertThat(viewModel.consumableViewEvents.value.first()).isEqualTo(
                    UiEvent.Navigate(
                        Screen.Home.route
                    )
                )
            }
        }

    private fun passNewSavedStateHandleWithNewEmail(
        newSavedStateHandle: SavedStateHandle
    ) {
        viewModel = UserInformationViewModel(
            savedStateHandle = newSavedStateHandle,
            firebaseUserCoreRepository = firebaseUserCoreRepository,
            authRepository = authRepository,
            validatePasswordUseCase = ValidatePasswordUseCase(),
            checkIfExistUserWithTheSameUsernameUseCase = CheckIfExistUserWithTheSameUsernameUseCase(
                firebaseUserCoreRepository
            ),
            coreUserPreferencesRepository = coreUserPreferencesRepository,
            validateUsernameUseCase = ValidateUsernameUseCase()
        )
    }
}