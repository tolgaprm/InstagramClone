package com.prmto.share_presentation.postPreview

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.core.app.ApplicationProvider
import com.prmto.share_presentation.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostPreviewScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testPreviewTitle() {
        launchPostPreviewScreen()

        composeTestRule.onNodeWithContentDescription(context.getString(R.string.preview))
            .assertIsDisplayed()
    }

    private fun launchPostPreviewScreen() {
        composeTestRule.setContent {
            PostPreviewScreen(
                postPreviewPhotoUris = listOf(),
                onCloseClick = {}
            )
        }
    }
}