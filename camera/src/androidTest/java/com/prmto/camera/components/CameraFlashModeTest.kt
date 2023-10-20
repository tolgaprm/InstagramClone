package com.prmto.camera.components

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.core.app.ApplicationProvider
import com.prmto.camera.R
import com.prmto.camera.util.CameraFlashMode
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CameraFlashModeTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun isVisibleCameraFlashModeFalse_NotExists() {
        composeTestRule.setContent {
            CameraFlashModeButton(
                isVisibleCameraFlashMode = false,
                cameraFlashMode = CameraFlashMode.OFF,
                onClickFlashMode = {}
            )
        }
        val contentDescription = context.getString(R.string.flash_off)
        composeTestRule.onNodeWithContentDescription(contentDescription).assertDoesNotExist()
    }

    @Test
    fun whenCameraFlashModelOn_checkContentDescription() {
        composeTestRule.setContent {
            CameraFlashModeButton(
                isVisibleCameraFlashMode = true,
                cameraFlashMode = CameraFlashMode.ON,
                onClickFlashMode = {}
            )
        }
        val contentDescription = context.getString(R.string.flash_on)
        composeTestRule.onNodeWithContentDescription(contentDescription).assertIsDisplayed()
    }
}