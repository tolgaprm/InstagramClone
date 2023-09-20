package com.prmto.instagramclone

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.prmto.auth_presentation.util.AuthTestTags
import com.prmto.home_presentation.util.HomeTestTags
import com.prmto.instagramclone.navigation.InstaApp
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationTest {
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule(order = 0)
    val hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun whenUserIsNotLoggedInFirstScreenIsLogin() {
        composeTestRule.activity.setContent {
            InstaApp(isUserLoggedIn = false)
        }
        composeTestRule.onNodeWithTag(AuthTestTags.LOGIN_SCREEN).assertExists()
    }

    @Test
    fun whenUserIsLoggedInFirstScreenIsHome() {
        composeTestRule.activity.setContent {
            InstaApp(isUserLoggedIn = true)
        }
        composeTestRule.onNodeWithTag(HomeTestTags.HOME_SCREEN).assertExists()
    }
}