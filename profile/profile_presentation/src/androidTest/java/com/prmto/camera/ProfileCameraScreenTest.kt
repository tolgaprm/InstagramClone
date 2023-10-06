package com.prmto.camera

import android.Manifest
import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme
import com.prmto.profile_presentation.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.prmto.permission.R as PermissionR

@OptIn(ExperimentalPermissionsApi::class)
class ProfileCameraScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun isVisibleCameraFlashModeTrue_cameraFlashModeOn() {
        launchProfileCameraScreen(
            uiState = ProfileCameraUiState(
                isVisibleCameraFlashMode = true,
                cameraFlashMode = CameraFlashMode.ON
            )
        )
        val cameraFlashModeOn = context.getString(R.string.flash_on)
        composeTestRule.onNodeWithContentDescription(cameraFlashModeOn).assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun isVisibleCameraFlashModeTrue_cameraFlashModeOff() {
        launchProfileCameraScreen(
            uiState = ProfileCameraUiState(
                isVisibleCameraFlashMode = true,
                cameraFlashMode = CameraFlashMode.OFF
            )
        )
        val cameraFlashModeOn = context.getString(R.string.flash_off)
        composeTestRule.onNodeWithContentDescription(cameraFlashModeOn).assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun isVisibleCameraFlashModeTrue_cameraFlashModeAuto() {
        launchProfileCameraScreen(
            uiState = ProfileCameraUiState(
                isVisibleCameraFlashMode = true,
                cameraFlashMode = CameraFlashMode.AUTO
            )
        )
        val cameraFlashModeOn = context.getString(R.string.flash_auto)
        composeTestRule.onNodeWithContentDescription(cameraFlashModeOn).assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun cameraSwitchButton() {
        launchProfileCameraScreen()
        val cameraSwitch = context.getString(R.string.flip_camera)
        composeTestRule.onNodeWithContentDescription(cameraSwitch).assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun allPermissionsGrantedFalse_ShowPermissionDialog() {
        launchProfileCameraScreen(
            allPermissionsGranted = false,
            permissionState = listOf(CameraPermissionState()),
            dialogQueue = listOf(Manifest.permission.CAMERA)
        )
        val permissionRationaleTitle =
            context.getString(PermissionR.string.camera_permission_rationale_title)
        val permissionRationaleMessage =
            context.getString(PermissionR.string.camera_permission_rationale_message)
        val ok = context.getString(PermissionR.string.ok)
        composeTestRule.onNodeWithText(permissionRationaleTitle).assertIsDisplayed()
        composeTestRule.onNodeWithText(permissionRationaleMessage).assertIsDisplayed()
        composeTestRule.onNodeWithText(ok).assertIsDisplayed().assertHasClickAction()
    }

    @Test
    fun allPermissionsGrantedFalse_PermissionPermanentlyDeclined_ShowPermissionDialog() {
        launchProfileCameraScreen(
            allPermissionsGranted = false,
            permissionState = listOf(CameraPermissionState(shouldShowRationale = false)),
            dialogQueue = listOf(Manifest.permission.CAMERA)
        )

        val cameraText = context.getString(PermissionR.string.camera)
        val permissionPermanentlyDeclinedMessage = context.getString(
            PermissionR.string.permission_permanently_declined_message,
            cameraText
        )

        val goToSettings = context.getString(PermissionR.string.go_to_settings)
        composeTestRule.onNodeWithText(permissionPermanentlyDeclinedMessage).assertIsDisplayed()
        composeTestRule.onNodeWithText(goToSettings).assertIsDisplayed().assertHasClickAction()
    }

    private fun launchProfileCameraScreen(
        uiState: ProfileCameraUiState = ProfileCameraUiState(),
        allPermissionsGranted: Boolean = true,
        dialogQueue: List<String> = emptyList(),
        permissionState: List<PermissionState> = emptyList()
    ) {
        composeTestRule.setContent {
            InstagramCloneTheme {
                ProfileCameraScreen(
                    profileCameraUiState = uiState,
                    allPermissionsGranted = allPermissionsGranted,
                    permissionStates = permissionState,
                    dialogQueue = dialogQueue,
                    onChangeCamera = {},
                    onStartCamera = {},
                    onTakePhoto = {},
                    onPopBackStack = {},
                    onEvent = {}
                )
            }
        }
    }

    private class CameraPermissionState(
        val shouldShowRationale: Boolean = true,
    ) : PermissionState {
        override val permission: String
            get() = Manifest.permission.CAMERA
        override val status: PermissionStatus
            get() = PermissionStatus.Denied(shouldShowRationale = shouldShowRationale)

        override fun launchPermissionRequest() {}
    }
}