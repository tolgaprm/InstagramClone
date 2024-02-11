package com.prmto.share_presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class SharePostTopAppBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testTopAppBarTitle() {
        composeTestRule.setContent {
            SharePostTopAppBar(title = "Shared Post Title")
        }

        composeTestRule.onNodeWithText("Shared Post Title").assertIsDisplayed()
    }

    @Test
    fun testTopAppBarNavigationIcon() {
        composeTestRule.setContent {
            SharePostTopAppBar(
                title = "Shared Post Title",
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            )
        }

        composeTestRule.onNodeWithContentDescription("Back").assertIsDisplayed()
    }

    @Test
    fun testTopAppBarActions() {
        composeTestRule.setContent {
            SharePostTopAppBar(
                title = "Shared Post Title",
                actions = {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send"
                    )
                }
            )
        }

        composeTestRule.onNodeWithContentDescription("Send").assertIsDisplayed()
    }

    @Test
    fun testTitle_NavigationIcon_Actions() {
        composeTestRule.setContent {
            SharePostTopAppBar(
                title = "Shared Post Title",
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send"
                        )
                    }
                }
            )
        }

        composeTestRule.onNodeWithText("Shared Post Title").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back").assertIsDisplayed()
            .assertHasClickAction()
        composeTestRule.onNodeWithContentDescription("Send").assertIsDisplayed()
            .assertHasClickAction()
    }
}