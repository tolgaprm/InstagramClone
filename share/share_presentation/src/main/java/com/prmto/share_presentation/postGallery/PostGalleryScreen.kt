package com.prmto.share_presentation.postGallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.prmto.core_presentation.components.GalleryDropDownButton
import com.prmto.core_presentation.components.GalleryScreenBox
import com.prmto.core_presentation.previews.UiModePreview
import com.prmto.core_presentation.ui.theme.InstaBlue
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme
import com.prmto.permission.provider.getPermissionInfoProvider
import com.prmto.permission.util.HandlePermissionStatus
import com.prmto.permission.util.handleFilePermissionAccess
import com.prmto.permission.util.permissionToRequestForFile
import com.prmto.share_presentation.R
import com.prmto.share_presentation.components.CloseButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PostGalleryRoute(
    modifier: Modifier = Modifier,
    onNavigateToPostCamera: () -> Unit
) {
    val permissionState = handleFilePermissionAccess()
    val permissionProvider = getPermissionInfoProvider(permissionToRequestForFile())
    PostGalleryScreen(
        modifier = modifier,
        permissionIsGranted = permissionState.status.isGranted,
        handlePermission = {
            HandlePermissionStatus(
                permissionState = permissionState,
                permissionProvider = permissionProvider
            )
        },
        onClickedCameraButton = onNavigateToPostCamera
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostGalleryScreen(
    modifier: Modifier = Modifier,
    permissionIsGranted: Boolean = false,
    onClickedCameraButton: () -> Unit,
    handlePermission: @Composable () -> Unit = {},
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = false)
    )
    val coroutineScope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {

        },
        topBar = {
            PostGalleryTopSection(
                onCloseClick = {},
                onNextClick = {}
            )
        },
        content = {
            GalleryScreenBox(
                modifier = modifier,
                permissionIsGranted = permissionIsGranted,
                selectedImageUri = null,
                urisInSelectedAlbum = listOf(),
                onClickImageItem = {},
                errorMessage = null,
                addComposableOfTheCenter = {
                    PostGalleryCenterSection(
                        selectedDirectoryName = "Recents",
                        onClickedDropDown = {
                            coroutineScope.launch {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            }
                        },
                        onClickedCameraButton = onClickedCameraButton,
                        onClickMultipleSelectButton = {}
                    )
                },
                handlePermission = handlePermission
            )
        }
    )
}

@Composable
private fun PostGalleryTopSection(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CloseButton(
                onCloseClick = onCloseClick
            )
            Text(
                stringResource(R.string.new_post),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        TextButton(onClick = onNextClick) {
            Text(
                text = stringResource(R.string.next),
                color = Color.InstaBlue,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun PostGalleryCenterSection(
    modifier: Modifier = Modifier,
    selectedDirectoryName: String,
    onClickedDropDown: () -> Unit,
    onClickedCameraButton: () -> Unit,
    onClickMultipleSelectButton: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(vertical = 4.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        GalleryDropDownButton(
            selectedDirectoryName = selectedDirectoryName,
            onClicked = onClickedDropDown
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            MultipleSelectedButton(
                isEnabledMultipleSelectMode = false,
                onClicked = onClickMultipleSelectButton
            )
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(
                onClick = onClickedCameraButton,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.White.copy(alpha = 0.4f)
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.CameraAlt,
                    contentDescription = stringResource(R.string.navigate_to_post_camera_screen),
                )
            }
        }
    }
}

@Composable
private fun MultipleSelectedButton(
    modifier: Modifier = Modifier,
    isEnabledMultipleSelectMode: Boolean = false,
    onClicked: () -> Unit
) {
    val containerColor =
        if (isEnabledMultipleSelectMode) Color.InstaBlue else Color.White.copy(alpha = 0.4f)
    IconButton(
        modifier = modifier,
        onClick = onClicked,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = containerColor
        )
    ) {
        Icon(
            imageVector = Icons.Default.ContentCopy,
            contentDescription = stringResource(R.string.select_multiple_images_or_videos)
        )
    }
}

@UiModePreview
@Composable
fun PostGalleryScreenPreview() {
    InstagramCloneTheme {
        Surface {
            PostGalleryScreen(
                onClickedCameraButton = {}
            )
        }
    }
}