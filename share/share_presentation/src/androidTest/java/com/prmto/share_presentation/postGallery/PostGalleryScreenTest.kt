package com.prmto.share_presentation.postGallery

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.core.net.toUri
import androidx.test.core.app.ApplicationProvider
import com.prmto.core_domain.usecase.AlbumAndCoverImage
import com.prmto.share_presentation.R
import com.prmto.share_presentation.postGallery.components.postGallerySheetContentTestTag
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostGalleryScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun shareTopAppBarTitle_newPost() {
        launchPostGalleryScreen()
        composeRule.onNodeWithText(context.getString(R.string.new_post)).assertIsDisplayed()
    }

    @Test
    fun closeButton_isDisplayed_hasClickAction() {
        launchPostGalleryScreen()
        composeRule.onNodeWithText(context.getString(R.string.close))
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun nextButton_isDisplayed_hasClickAction() {
        launchPostGalleryScreen()
        composeRule.onNodeWithText(context.getString(R.string.next))
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun selectedAlbumName_isDisplayed_hasClickAction() {
        val uiState = PostGalleryUiState(
            mediaAlbumNames = listOf("Album 1", "Album 2"),
            selectedAlbumName = "Album 1"
        )
        launchPostGalleryScreen(uiState)
        composeRule.onNodeWithText(uiState.selectedAlbumName)
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun multipleSelectedButton_isDisplayed_hasClickAction() {
        launchPostGalleryScreen()
        composeRule.onNodeWithContentDescription(context.getString(R.string.select_multiple_images_or_videos))
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun cameraButton_isDisplayed_hasClickAction() {
        launchPostGalleryScreen()
        composeRule.onNodeWithContentDescription(context.getString(R.string.navigate_to_post_camera_screen))
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun whenSelectedAlbumNameClicked_showPostGallerySheetContent() {
        val uiState = PostGalleryUiState(
            mediaAlbumNames = listOf("Album 1", "Album 2"),
            selectedAlbumName = "Album 1"
        )
        launchPostGalleryScreen(uiState = uiState)

        composeRule.onNodeWithText(uiState.selectedAlbumName).performClick()

        composeRule.onNodeWithTag(postGallerySheetContentTestTag)
            .assertIsDisplayed()
    }

    @Test
    fun testAlbumSelectionAndGallerySheetVisibility() {
        val uiState = PostGalleryUiState(
            albumAndCoverImages = listOf(
                AlbumAndCoverImage(
                    albumName = "Photos",
                    firstImageUri = "https://picsum.photos/200/300".toUri()
                ),
                AlbumAndCoverImage(
                    albumName = "Screenshots",
                    firstImageUri = "https://picsum.photos/200/300".toUri()
                )
            ),
            mediaAlbumNames = listOf("Album 1", "Album 2"),
            selectedAlbumName = "Album 1"
        )

        launchPostGalleryScreen(uiState = uiState)

        composeRule.onNodeWithText(uiState.selectedAlbumName).performClick()

        composeRule.onNodeWithTag(postGallerySheetContentTestTag)
            .assertIsDisplayed()

        composeRule.onNodeWithText("Screenshots").performClick()

        composeRule.onNodeWithTag(postGallerySheetContentTestTag)
            .assertIsNotDisplayed()
    }


    private fun launchPostGalleryScreen(
        uiState: PostGalleryUiState = PostGalleryUiState(),
        isPermissionGranted: Boolean = true
    ) {
        composeRule.setContent {
            PostGalleryScreen(
                uiState = uiState,
                permissionIsGranted = isPermissionGranted,
                onClickedCameraButton = {},
                onCloseClick = {},
                onNextClick = {},
                onEvent = {},
                handlePermission = {}
            )
        }
    }
}