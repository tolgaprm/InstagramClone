package com.prmto.instagramclone

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoActivityResumedException
import com.prmto.auth_presentation.util.AuthTestTags
import com.prmto.core_presentation.navigation.Screen
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
        setContentWithUserLoggedIn()
        composeTestRule.homeScreenTopMatcher().assertExists()
    }

    @Test(expected = NoActivityResumedException::class)
    fun backFromHomeDestinationQuitsApp() {
        setContentWithUserLoggedIn()
        composeTestRule.onNodeWithTag(Screen.Search.route).performClick()
        composeTestRule.onNodeWithTag(Screen.Home.route).performClick()
        // WHEN the user uses the system button/gesture to go back
        Espresso.pressBack()
        // THEN the app quits
    }

    @Test
    fun backFromDestinationReturnsToForYou() {
        setContentWithUserLoggedIn()
        composeTestRule.onNodeWithTag(Screen.Search.route).performClick()
        composeTestRule.onNodeWithTag(Screen.Share.route).performClick()
        // WHEN the user uses the system button/gesture to go back,
        Espresso.pressBack()
        // THEN the app shows the Home destination
        composeTestRule.homeScreenTopMatcher().assertExists()
    }


    private fun setContentWithUserLoggedIn() {
        composeTestRule.activity.setContent {
            InstaApp(isUserLoggedIn = true)
        }
    }

    private fun ComposeTestRule.homeScreenTopMatcher() =
        composeTestRule.onNodeWithTag(HomeTestTags.HOME_SCREEN)

}