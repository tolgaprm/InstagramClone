package com.prmto.edit_profile_presentation

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import com.prmto.core_testing.userDetail
import com.prmto.profile_presentation.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EditProfileScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext<Context>()
    }

    @Test
    fun stateUserDetailFull_ShowAllText() {
        setEditProfileScreen(uiState = EditProfileUiState(updatedUserDetail = userDetail()))

        composeTestRule.onNodeWithText("username").assertIsDisplayed()
        composeTestRule.onNodeWithText("test_name").assertIsDisplayed()
        composeTestRule.onNodeWithText("test_bio").assertIsDisplayed()
        composeTestRule.onNodeWithText("test_website").assertIsDisplayed()
    }

    @Test
    fun navigationIcon_ShowAndHasClickAction() {
        setEditProfileScreen()
        val navigationIconContentDescription = context.getString(R.string.close)
        composeTestRule.onNodeWithContentDescription(navigationIconContentDescription)
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun stateIsShowSaveButtonTrue_ShowSaveButton() {
        setEditProfileScreen(uiState = EditProfileUiState(isShowSaveButton = true))
        val showButtonContentDescription = context.getString(R.string.saved)

        composeTestRule.onNodeWithContentDescription(showButtonContentDescription)
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun stateIsShowSaveButtonFalse_DoesNotShowSaveButton() {
        setEditProfileScreen(uiState = EditProfileUiState(isShowSaveButton = false))
        val context = ApplicationProvider.getApplicationContext<Context>()
        val showButtonContentDescription = context.getString(R.string.saved)

        composeTestRule.onNodeWithContentDescription(showButtonContentDescription)
            .assertDoesNotExist()
    }

    @Test
    fun test_changeProfilePhotoText_HasClickAction() {
        setEditProfileScreen()
        val changeProfilePhotoText = context.getString(R.string.change_profile_photo)
        composeTestRule.onNodeWithText(changeProfilePhotoText).assertIsDisplayed()
        composeTestRule.onNodeWithText(changeProfilePhotoText).assertHasClickAction()
    }

    @Test
    fun click_changeProfilePhotoText_ShowBottomSheetElements() {
        setEditProfileScreen()
        val changeProfilePhotoText = context.getString(R.string.change_profile_photo)
        val cameraBottomSheetText = context.getString(R.string.take_a_photo_from_camera)
        val galleryBottomSheetText = context.getString(R.string.choose_a_photo_from_gallery)
        composeTestRule.onNodeWithText(changeProfilePhotoText).performClick()
        composeTestRule.onNodeWithText(cameraBottomSheetText).assertIsDisplayed()
        composeTestRule.onNodeWithText(galleryBottomSheetText).assertIsDisplayed()
    }

    private fun setEditProfileScreen(
        uiState: EditProfileUiState = EditProfileUiState()
    ) {
        composeTestRule.setContent {
            EditProfileScreen(
                uiState = uiState,
                onPopBackStack = { },
                onNavigateToProfileCamera = { },
                onNavigateToGallery = { },
                onEvent = {}
            )
        }
    }
}