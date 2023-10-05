package com.prmto.common.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class ProfileTopBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun titleTextIsNotNull_ShowTitleText() {
        val titleText = "Title"
        setProfileTopBar(titleText = titleText)
        composeTestRule.onNodeWithText(titleText).assertIsDisplayed()
    }

    @Test
    fun titleComposableIsNotNull_ShowTitleComposable() {
        setProfileTopBar(
            titleComposable = {
                Text(text = "Title Composable")
            }
        )
        composeTestRule.onNodeWithText("Title Composable").assertIsDisplayed()
    }

    @Test
    fun actionsIsNotNull_ShowActionsComposable() {
        setProfileTopBar(
            actions = {
                Text(text = "Actions Composable")
            }
        )
        composeTestRule.onNodeWithText("Actions Composable").assertIsDisplayed()
    }

    private fun setProfileTopBar(
        titleText: String? = null,
        titleComposable: @Composable () -> Unit = {},
        actions: @Composable RowScope.() -> Unit = {},
    ) {
        composeTestRule.setContent {
            ProfileTopBar(
                onPopBackStack = {},
                titleText = titleText,
                actions = actions,
                titleComposable = titleComposable
            )
        }
    }
}