package com.prmto.profile_presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prmto.core_domain.constants.UiText
import com.prmto.core_domain.model.UserData
import com.prmto.core_presentation.util.UiEvent
import com.prmto.core_testing.fake_repository.preferences.CoreUserPreferencesRepositoryFake
import com.prmto.core_testing.fake_repository.user.FakeFirebaseUserCoreRepository
import com.prmto.core_testing.userData
import com.prmto.core_testing.userDetail
import com.prmto.core_testing.util.MainDispatcherRule
import com.prmto.core_testing.util.TestConstants
import com.prmto.profile_presentation.navigation.args.ProfileArgs.Companion.profileArgsUsername
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ProfileViewModel
    private lateinit var coreUserCoreRepository: FakeFirebaseUserCoreRepository
    private lateinit var coreUserPreferencesRepository: CoreUserPreferencesRepositoryFake
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setUp() {
        coreUserCoreRepository = FakeFirebaseUserCoreRepository()
        coreUserPreferencesRepository = CoreUserPreferencesRepositoryFake()
        savedStateHandle = SavedStateHandle(
            mapOf(profileArgsUsername to "test2_username")
        )
        viewModel = ProfileViewModel(
            userCoreRepository = coreUserCoreRepository,
            coreUserPreferencesRepository = coreUserPreferencesRepository,
            savedStateHandle = savedStateHandle
        )
    }

    @Test
    fun savedStateUsernameArgIsNotNull_stateUpdated() = runTest {
        val userUid = UUID.randomUUID().toString()
        coreUserCoreRepository.userListInFirebase = mutableListOf(
            userData(userDetail = userDetail().copy(username = "test1_username")),
            userData(
                userUid = userUid, userDetail = userDetail().copy(username = "test2_username")
            ),
            userData(userDetail = userDetail().copy(username = "test3_username"))
        )
        val expectedUserData =
            userData(userUid = userUid, userDetail = userDetail().copy(username = "test2_username"))
        viewModel.uiState.test {
            val firstEmittedValue = awaitItem()
            assertThat(firstEmittedValue.isOwnProfile).isFalse()
            assertThat(firstEmittedValue.isLoading).isTrue()
            advanceUntilIdle()
            val secondEmittedValue = awaitItem()
            assertThat(secondEmittedValue.isLoading).isFalse()
            assertThat(secondEmittedValue.userData).isEqualTo(expectedUserData)
        }
    }

    @Test
    fun savedStateUsernameArgIsNotNull_returnError_stateUpdated() = runTest {
        coreUserCoreRepository.userListInFirebase = mutableListOf(
            userData(userDetail = userDetail().copy(username = "test_username")),
            userData(userDetail = userDetail().copy(username = "test1_username")),
            userData(userDetail = userDetail().copy(username = "test3_username"))
        )
        viewModel.uiState.test {
            val firstEmittedValue = awaitItem()
            assertThat(firstEmittedValue.isOwnProfile).isFalse()
            assertThat(firstEmittedValue.isLoading).isTrue()
            advanceUntilIdle()
            val secondEmittedValue = awaitItem()
            assertThat(secondEmittedValue.isLoading).isFalse()
        }
        assertThat(viewModel.consumableViewEvents.value.first()).isEqualTo(
            UiEvent.ShowMessage(
                UiText.DynamicString(TestConstants.USERNAME_DOES_NOT_EXIST_ERROR)
            )
        )
    }

    @Test
    fun savedStateUsernameArgIsNull_stateUpdated() = runTest {
        savedStateHandle = SavedStateHandle(
            mapOf(profileArgsUsername to null)
        )
        setNewViewModelWithSavedStateHandle(savedStateHandle)
        val userDetail = userDetail()
        coreUserPreferencesRepository.userDetailList = listOf(userDetail)
        viewModel.uiState.test {
            val firstEmittedValue = awaitItem()
            assertThat(firstEmittedValue.isOwnProfile).isTrue()
            assertThat(firstEmittedValue.isLoading).isTrue()
            advanceUntilIdle()
            val secondEmittedValue = awaitItem()
            assertThat(secondEmittedValue.isLoading).isFalse()
            assertThat(secondEmittedValue.userData).isEqualTo(UserData(userDetail = userDetail))
        }
    }

    private fun setNewViewModelWithSavedStateHandle(
        savedStateHandle: SavedStateHandle
    ) {
        viewModel = ProfileViewModel(
            userCoreRepository = coreUserCoreRepository,
            coreUserPreferencesRepository = coreUserPreferencesRepository,
            savedStateHandle = savedStateHandle
        )
    }
}