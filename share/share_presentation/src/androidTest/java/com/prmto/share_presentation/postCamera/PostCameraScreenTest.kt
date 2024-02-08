package com.prmto.share_presentation.postCamera

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.core.app.ApplicationProvider
import com.prmto.camera.util.CameraFlashMode
import com.prmto.share_presentation.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostCameraScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun onCloseButtonIsVisibleAndHasClickAction() {
        launchPostCameraScreen()
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.close))
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun cameraFlashModeButton_isVisible() {
        // We don't write all scenarios of camera flash mode,
        // because we write the test for the same in the camera module for cameraFlashModeButton
        launchPostCameraScreen(
            uiState = PostCameraUiState(
                cameraFlashMode = CameraFlashMode.ON
            )
        )
        val cameraFlashModeOn = context.getString(com.prmto.camera.R.string.flash_on)
        composeTestRule.onNodeWithContentDescription(cameraFlashModeOn).assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun settingButton_isVisible_hasClickAction() {
        launchPostCameraScreen()
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.settings))
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun changeCameraButton_isVisible_hasClickAction() {
        launchPostCameraScreen()
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.change_camera))
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    private fun launchPostCameraScreen(
        uiState: PostCameraUiState = PostCameraUiState()
    ) {
        composeTestRule.setContent {
            PostCameraScreen(
                uiState = uiState,
                onStartCamera = {},
                onTakePhoto = { },
                onClickChangeCamera = { },
                onClickClose = { },
                onClickGallery = { },
                onEvent = {}
            )
        }
    }
}