package com.prmto.edit_profile_presentation

import android.net.Uri
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prmto.core_domain.constants.UiText
import com.prmto.core_domain.model.UserData
import com.prmto.core_domain.model.UserDetail
import com.prmto.core_domain.repository.auth.FirebaseAuthCoreRepository
import com.prmto.core_domain.usecase.CheckIfExistUserWithTheSameUsernameUseCase
import com.prmto.core_domain.usecase.GetCurrentUserUseCase
import com.prmto.core_presentation.util.UiEvent
import com.prmto.core_testing.fake_repository.preferences.CoreUserPreferencesRepositoryFake
import com.prmto.core_testing.fake_repository.user.FakeFirebaseUserCoreRepository
import com.prmto.core_testing.userData
import com.prmto.core_testing.util.MainDispatcherRule
import com.prmto.core_testing.util.TestConstants
import com.prmto.edit_profile_presentation.event.EditProfileUiEvent
import com.prmto.profile_domain.usecase.EditProfileUseCases
import com.prmto.profile_domain.usecase.ValidateWebSiteUrlUseCase
import com.prmto.profile_presentation.R
import com.repository.FakeProfileRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.UUID

@ExperimentalCoroutinesApi
class EditProfileViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: EditProfileViewModel
    private lateinit var coreUserPreferencesRepository: CoreUserPreferencesRepositoryFake
    private lateinit var firebaseUserCoreRepository: FakeFirebaseUserCoreRepository
    private lateinit var authCoreRepository: FirebaseAuthCoreRepository
    private lateinit var editProfileUseCases: EditProfileUseCases

    @Before
    fun setUp() {
        mockkStatic(Uri::class)
        authCoreRepository = mockk()
        coreUserPreferencesRepository = CoreUserPreferencesRepositoryFake()
        firebaseUserCoreRepository = FakeFirebaseUserCoreRepository()
        editProfileUseCases = EditProfileUseCases(
            getCurrentUser = GetCurrentUserUseCase(
                authCoreRepository = authCoreRepository
            ),
            checkIfExistUserWithTheSameUsername = CheckIfExistUserWithTheSameUsernameUseCase(
                firebaseUserCoreRepository = firebaseUserCoreRepository
            ),
            validateWebSiteUrl = ValidateWebSiteUrlUseCase()
        )
        viewModel = EditProfileViewModel(
            coreUserPreferencesRepository = coreUserPreferencesRepository,
            firebaseUserCoreRepository = firebaseUserCoreRepository,
            profileRepository = FakeProfileRepository(),
            editProfileUseCases = editProfileUseCases
        )
        setDefaultUserDetail(TestConstants.listOfUserData.map { it.userDetail })
    }

    @Test
    fun viewModelInit_stateUpdated() = runTest {
        viewModel.uiState.test {
            assertThat(awaitItem().isLoading).isTrue()
            advanceUntilIdle()
            val uiState = awaitItem()
            assertThat(uiState.isLoading).isFalse()
            assertThat(uiState.userDetail).isEqualTo(TestConstants.listOfUserData.first().userDetail)
        }
    }

    @Test
    fun eventIsEnteredName_stateUpdated() = runTest {
        val name = "name"
        val event = EditProfileUiEvent.EnteredName(name = name)

        viewModel.onEvent(event = event)
        viewModel.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState.updatedUserDetail.name).isEqualTo("name")
            assertThat(uiState.isShowSaveButton).isTrue()
        }
    }

    @Test
    fun eventIsEnteredUsername_stateUpdatedAndTrim() = runTest {
        val username = "  username   "
        val event = EditProfileUiEvent.EnteredUsername(username = username)

        viewModel.onEvent(event = event)
        viewModel.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState.updatedUserDetail.username).isEqualTo("username")
            assertThat(uiState.isShowSaveButton).isTrue()
        }
    }

    @Test
    fun eventIsEnteredBio_stateUpdated() = runTest {
        val bio = "bio"
        val event = EditProfileUiEvent.EnteredBio(bio = bio)

        viewModel.onEvent(event = event)
        viewModel.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState.updatedUserDetail.bio).isEqualTo("bio")
            assertThat(uiState.isShowSaveButton).isTrue()
        }
    }

    @Test
    fun eventIsEnteredWebsite_stateUpdated() = runTest {
        val website = "website"
        val event = EditProfileUiEvent.EnteredWebsite(website = website)

        viewModel.onEvent(event = event)
        viewModel.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState.updatedUserDetail.webSite).isEqualTo("website")
            assertThat(uiState.isShowSaveButton).isTrue()
        }
    }

    @Test
    fun eventIsSelectNewProfileImage_stateUpdated() = runTest {
        val uri = "exampleUriString"
        val event =
            EditProfileUiEvent.SelectNewProfileImage(selectNewProfileUriString = uri)
        every { Uri.parse(any()) } returns mockk { every { lastPathSegment } returns uri }
        viewModel.onEvent(event)
        viewModel.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState.selectedNewProfileImage).isEqualTo(Uri.parse(uri))
            assertThat(uiState.isShowSaveButton).isTrue()
        }
    }

    @Test
    fun eventIsUpdateProfileInfo_usernameOrNameIsBlank_AddUiEvent() = runTest {
        viewModel.onEvent(EditProfileUiEvent.EnteredName(""))
        viewModel.onEvent(EditProfileUiEvent.UpdateProfileInfo)
        assertThat(viewModel.consumableViewEvents.value.first()).isEqualTo(
            UiEvent.ShowMessage(
                UiText.StringResource(R.string.username_or_name_fields_are_not_empty)
            )
        )
    }

    @Test
    fun eventIsUpdateProfileInfo_userIsLogin_existsSameUsername() = runTest {
        val userUid = setUserLogin()
        // Add user data to the FakeFirebaseUserCoreRepository.
        setUserDataListToFakeFirebaseUserCoreRepository(
            listOf(
                userData(),
                userData(userUid = userUid)
            )
        )

        // Trigger ViewModel events.
        viewModel.onEvent(EditProfileUiEvent.EnteredName("Name surname"))
        viewModel.onEvent(EditProfileUiEvent.EnteredUsername("username"))
        viewModel.onEvent(EditProfileUiEvent.UpdateProfileInfo)

        // Set up expectations to test ViewModel's UI state.
        viewModel.uiState.test {
            awaitItem()
            val uiState = awaitItem()
            assertThat(uiState.isLoading).isFalse()
            assertThat(viewModel.consumableViewEvents.value.first()).isEqualTo(
                UiEvent.ShowMessage(UiText.StringResource(com.prmto.core_domain.R.string.username_already_exists))
            )
        }
    }

    @Test
    fun eventIsUpdateProfileInfo_userIsLogin_updateProfileInfo() = runTest {
        val userUid = setUserLogin()
        // Add user data to the FakeFirebaseUserCoreRepository.
        setUserDataListToFakeFirebaseUserCoreRepository(
            listOf(
                userData(),
                userData(userUid = userUid)
            )
        )

        // Trigger ViewModel events.
        viewModel.onEvent(EditProfileUiEvent.EnteredName("Name surname"))
        viewModel.onEvent(EditProfileUiEvent.EnteredUsername("username4")) // username4 is not in the list of users.
        viewModel.onEvent(EditProfileUiEvent.EnteredWebsite("http://example.com"))
        viewModel.onEvent(EditProfileUiEvent.UpdateProfileInfo)

        // Set up expectations to test ViewModel's UI state.
        viewModel.uiState.test {
            // Check if the expected loading state is true.
            assertThat(awaitItem().isLoading).isTrue()
            advanceUntilIdle()

            // Get the UI state.
            val uiState = awaitItem()

            // Check updated user data.
            val updatedUserData =
                firebaseUserCoreRepository.userListInFirebase.find { it.userUid == userUid }
            assertThat(updatedUserData?.userDetail?.name).isEqualTo("Name surname")
            assertThat(updatedUserData?.userDetail?.username).isEqualTo("username4")
            assertThat(updatedUserData?.userDetail?.webSite).isEqualTo("http://example.com")

            // Check updated user details in user preferences.
            assertThat(coreUserPreferencesRepository.userDetailList).contains(uiState.updatedUserDetail)

            // Check if loading state is false.
            assertThat(uiState.isLoading).isFalse()

            // Check the consumable event triggered by the ViewModel.
            assertThat(viewModel.consumableViewEvents.value.first()).isEqualTo(UiEvent.PopBackStack)
        }
    }

    @Test
    fun eventIsUpdateProfileInfo_userIsLogin_websiteInvalid() = runTest {
        val userUid = setUserLogin()
        // Add user data to the FakeFirebaseUserCoreRepository.
        setUserDataListToFakeFirebaseUserCoreRepository(
            listOf(
                userData(),
                userData(userUid = userUid)
            )
        )

        // Trigger ViewModel events.
        viewModel.onEvent(EditProfileUiEvent.EnteredName("Name surname"))
        viewModel.onEvent(EditProfileUiEvent.EnteredUsername("Tolga")) // username4 is not in the list of users.
        viewModel.onEvent(EditProfileUiEvent.EnteredWebsite("http://example .com")) // web site is invalid.
        viewModel.onEvent(EditProfileUiEvent.UpdateProfileInfo)

        // Set up expectations to test ViewModel's UI state.
        viewModel.uiState.test {
            // Check if the expected loading state is true.
            awaitItem()
            advanceUntilIdle()
            val uiState = awaitItem()
            assertThat(uiState.isLoading).isFalse()
            assertThat(viewModel.consumableViewEvents.value.first()).isEqualTo(
                UiEvent.ShowMessage(UiText.StringResource(R.string.invalid_website))
            )
        }
    }

    private fun setUserLogin(): String {
        // Mock a user as if they are logged in.
        every { authCoreRepository.currentUser() } returns mockk()

        // Generate a user UID.
        val userUid = UUID.randomUUID().toString()

        // Associate the user's UID with the mock call above.
        every { authCoreRepository.currentUser()?.uid } returns userUid

        return userUid
    }

    private fun setDefaultUserDetail(
        userDetail: List<UserDetail>
    ) {
        coreUserPreferencesRepository.userDetailList = userDetail
    }

    private fun setUserDataListToFakeFirebaseUserCoreRepository(
        listOfUserData: List<UserData>
    ) {
        firebaseUserCoreRepository.userListInFirebase = listOfUserData.toMutableList()
    }
}