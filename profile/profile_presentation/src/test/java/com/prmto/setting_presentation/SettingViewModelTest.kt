package com.prmto.setting_presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import com.prmto.core_domain.constants.UiText
import com.prmto.core_domain.repository.auth.FirebaseAuthCoreRepository
import com.prmto.core_presentation.navigation.NestedNavigation
import com.prmto.core_presentation.util.UiEvent
import com.prmto.core_testing.fake_repository.preferences.CoreUserPreferencesRepositoryFake
import com.prmto.core_testing.userDetail
import com.prmto.core_testing.util.MainDispatcherRule
import com.prmto.profile_presentation.R
import com.prmto.setting_presentation.event.SettingScreenEvent
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SettingViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: SettingViewModel
    private lateinit var coreAuthRepository: FirebaseAuthCoreRepository
    private lateinit var coreUserPreferencesRepository: CoreUserPreferencesRepositoryFake

    @Before
    fun setUp() {
        coreAuthRepository = mockk(relaxed = true)
        coreUserPreferencesRepository = CoreUserPreferencesRepositoryFake()
        viewModel = SettingViewModel(
            coreAuthRepository = coreAuthRepository,
            coreUserPreferencesRepository = coreUserPreferencesRepository
        )
    }

    @Test
    fun `when event is Logout and user is not logged in, then UiEvent is ShowMessage`() = runTest {
        every { coreAuthRepository.currentUser() } returns null
        viewModel.onEvent(SettingScreenEvent.Logout)
        val expectedViewEvent = UiEvent.ShowMessage(
            UiText.StringResource(R.string.you_are_not_logged_in)
        )

        viewModel.consumableViewEvents.test {
            assertThat(awaitItem().first()).isEqualTo(expectedViewEvent)
        }
    }

    @Test
    fun `when event is Logout and logout and deleteUserDetail is success, then delete user detail`() =
        runTest {
            every { coreAuthRepository.currentUser() } returns mockk<FirebaseUser>(relaxed = true)
            coreUserPreferencesRepository.saveUserDetail(userDetail())
            viewModel.onEvent(SettingScreenEvent.Logout)
            viewModel.uiState.test {
                advanceUntilIdle()
                assertThat(coreUserPreferencesRepository.userDetailList).isEmpty()
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when event is Logout and logout and deleteUserDetail is error, then return UiEventShowMessage`() =
        runTest {
            every { coreAuthRepository.currentUser() } returns mockk<FirebaseUser>(relaxed = true)
            coreUserPreferencesRepository.isReturnError = true
            viewModel.onEvent(SettingScreenEvent.Logout)
            viewModel.consumableViewEvents.test {
                awaitItem()
                assertThat(awaitItem().first()).isInstanceOf(UiEvent.ShowMessage::class.java)
            }
        }

    @Test
    fun `when event is Logout and user already logged in, deleteUserDetail return success, then UiEvent is Navigate`() =
        runTest {
            every { coreAuthRepository.currentUser() } returns mockk<FirebaseUser>(relaxed = true)
            viewModel.onEvent(SettingScreenEvent.Logout)
            val expectedViewEvent = UiEvent.Navigate(
                NestedNavigation.Auth.route
            )
            viewModel.consumableViewEvents.test {
                awaitItem()
                assertThat(awaitItem().first()).isEqualTo(expectedViewEvent)
            }
        }
}