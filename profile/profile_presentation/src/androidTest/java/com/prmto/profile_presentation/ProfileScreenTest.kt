package com.prmto.profile_presentation

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import com.prmto.core_domain.model.dummyDataGenerator.statistics
import com.prmto.core_testing.userData
import com.prmto.core_testing.userDetail
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun stateIsOwnProfileTrue_EditProfileButtonDisplayed() {
        launchProfileScreen(uiState = ProfileUiState(isOwnProfile = true))
        val editProfileText = context.getString(R.string.edit_profile)
        composeTestRule.onNodeWithText(editProfileText).assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun stateIsOwnProfileFalse_EditProfileButtonNotExist() {
        launchProfileScreen(uiState = ProfileUiState(isOwnProfile = false))
        val editProfileText = context.getString(R.string.edit_profile)
        composeTestRule.onNodeWithText(editProfileText).assertDoesNotExist()
    }

    @Test
    fun userDetailState() {
        launchProfileScreen(
            uiState = ProfileUiState(
                userData = userData(
                    userDetail = userDetail().copy(
                        username = "test_username",
                        name = "test_name",
                        bio = "test_bio",
                        webSite = "test_website"
                    )
                )
            )
        )

        composeTestRule.onNodeWithText("test_username").assertIsDisplayed()
        composeTestRule.onNodeWithText("test_name").assertIsDisplayed()
        composeTestRule.onNodeWithText("test_bio").assertIsDisplayed()
        composeTestRule.onNodeWithText("test_website").assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun userStatistics() {
        launchProfileScreen(
            uiState = ProfileUiState(
                userData = userData(
                    statistics = statistics()
                        .copy(
                            followingCount = 100,
                            followersCount = 200,
                            postsCount = 300
                        )
                )
            )
        )
        composeTestRule.onNodeWithText("100").assertIsDisplayed()
        composeTestRule.onNodeWithText("200").assertIsDisplayed()
        composeTestRule.onNodeWithText("300").assertIsDisplayed()
    }

    private fun launchProfileScreen(
        uiState: ProfileUiState = ProfileUiState()
    ) {
        composeTestRule.setContent {
            ProfileScreen(
                uiState = uiState,
                onNavigateToSettingScreen = {},
                onNavigateToEditProfileScreen = {}
            )
        }
    }
}