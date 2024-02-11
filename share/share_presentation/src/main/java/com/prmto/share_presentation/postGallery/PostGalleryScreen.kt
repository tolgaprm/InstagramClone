package com.prmto.share_presentation.postGallery

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.prmto.camera.crop.CropActivityResultContract
import com.prmto.core_presentation.components.GalleryDropDownButton
import com.prmto.core_presentation.components.GalleryScreenBoxInPostGallery
import com.prmto.core_presentation.components.InstaIconButton
import com.prmto.core_presentation.previews.UiModePreview
import com.prmto.core_presentation.ui.HandleConsumableViewEvents
import com.prmto.core_presentation.ui.theme.InstaBlue
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme
import com.prmto.permission.provider.getPermissionInfoProvider
import com.prmto.permission.util.HandlePermissionStatus
import com.prmto.permission.util.handleFilePermissionAccess
import com.prmto.permission.util.permissionToRequestForFile
import com.prmto.share_presentation.R
import com.prmto.share_presentation.components.SharePostTopAppBar
import com.prmto.share_presentation.postGallery.components.PostGallerySheetContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PostGalleryRoute(
    modifier: Modifier = Modifier,
    onNavigateToPostCamera: () -> Unit,
    onNavigateToPostShare: (String) -> Unit,
    onPopBackStack: () -> Unit,
) {
    val context = LocalContext.current
    val viewModel: PostGalleryViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val consumableViewEvents by viewModel.consumableViewEvents.collectAsStateWithLifecycle()
    val permissionState = handleFilePermissionAccess(
        onPermissionGranted = { viewModel.onEvent(PostGalleryEvent.AllPermissionGranted) }
    )
    val permissionProvider = getPermissionInfoProvider(permissionToRequestForFile())
    val cropActivityLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(),
        onResult = { croppedUri ->
            croppedUri?.let {
                viewModel.onEvent(PostGalleryEvent.OnImageCropped(it))
            }
        }
    )
    val warningMessage = stringResource(R.string.please_you_select_an_image)

    PostGalleryScreen(
        modifier = modifier,
        uiState = uiState,
        permissionIsGranted = permissionState.status.isGranted,
        onClickedCameraButton = onNavigateToPostCamera,
        onCloseClick = onPopBackStack,
        onNextClick = {
            handleOnNextClick(
                context = context,
                isEnabledMultipleSelectMode = uiState.isActiveMultipleSelection,
                selectedImageUri = uiState.selectedImageUri,
                selectedUrisInEnabledMultipleSelectMode = uiState.selectedUrisInEnabledMultipleSelectMode,
                warningMessage = warningMessage,
                onLaunchCropActivity = { uri -> cropActivityLauncher.launch(uri) }
            )
        },
        onEvent = viewModel::onEvent,
        handlePermission = {
            HandlePermissionStatus(
                permissionState = permissionState, permissionProvider = permissionProvider
            )
        }
    )

    HandleConsumableViewEvents(
        consumableViewEvents = consumableViewEvents,
        onEventNavigate = { route ->
            onNavigateToPostShare(route)
        },
        onEventConsumed = viewModel::onEventConsumed
    )
}

private fun handleOnNextClick(
    context: Context,
    isEnabledMultipleSelectMode: Boolean,
    selectedImageUri: Uri?,
    selectedUrisInEnabledMultipleSelectMode: List<Uri>,
    warningMessage: String,
    onLaunchCropActivity: (Uri) -> Unit
) {
    if (isEnabledMultipleSelectMode) {
        if (selectedUrisInEnabledMultipleSelectMode.isNotEmpty()) {
            selectedUrisInEnabledMultipleSelectMode.forEach {
                onLaunchCropActivity(it)
            }
        } else {
            Toast.makeText(context, warningMessage, Toast.LENGTH_SHORT).show()
        }
    } else {
        selectedImageUri?.let {
            onLaunchCropActivity(it)
        } ?: run {
            Toast.makeText(
                context, warningMessage, Toast.LENGTH_SHORT
            ).show()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostGalleryScreen(
    modifier: Modifier = Modifier,
    uiState: PostGalleryUiState,
    permissionIsGranted: Boolean = false,
    onClickedCameraButton: () -> Unit,
    onCloseClick: () -> Unit,
    onNextClick: () -> Unit,
    onEvent: (PostGalleryEvent) -> Unit,
    handlePermission: @Composable () -> Unit = {},
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        )
    )
    val coroutineScope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            PostGallerySheetContent(
                albumsAndCoverImage = uiState.albumAndCoverImages,
                onClickedAlbumItem = {
                    onEvent(PostGalleryEvent.OnClickAlbumItem(it))
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.hide()
                    }
                }
            )
        },
        topBar = {
            SharePostTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.new_post),
                navigationIcon = {
                    InstaIconButton(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.close),
                        onClick = onCloseClick
                    )
                },
                actions = {
                    TextButton(onClick = onNextClick) {
                        Text(
                            text = stringResource(R.string.next),
                            color = Color.InstaBlue,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        },
        content = {
            GalleryScreenBoxInPostGallery(
                modifier = modifier,
                permissionIsGranted = permissionIsGranted,
                selectedImageUriShowOnTransformableImage = uiState.selectedImageUri,
                urisInSelectedAlbum = uiState.urisInSelectedAlbum,
                selectedUrisInEnabledMultipleSelectMode = uiState.selectedUrisInEnabledMultipleSelectMode,
                isEnableMultipleSelection = uiState.isActiveMultipleSelection,
                onClickImageItem = { selectedUri ->
                    onEvent(PostGalleryEvent.OnClickImageItem(selectedUri))
                },
                errorMessage = uiState.errorMessage,
                addComposableOfTheCenter = {
                    PostGalleryCenterSection(
                        selectedDirectoryName = uiState.selectedAlbumName,
                        isEnabledMultipleSelectMode = uiState.isActiveMultipleSelection,
                        onClickedDropDown = {
                            onEvent(PostGalleryEvent.OnOpenBottomSheet)
                            coroutineScope.launch {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            }
                        },
                        onClickedCameraButton = onClickedCameraButton,
                        onClickMultipleSelectButton = {
                            onEvent(PostGalleryEvent.OnClickMultipleSelectButton)
                        }
                    )
                },
                handlePermission = handlePermission
            )
        }
    )
}

@Composable
private fun PostGalleryCenterSection(
    modifier: Modifier = Modifier,
    selectedDirectoryName: String,
    isEnabledMultipleSelectMode: Boolean = false,
    onClickedDropDown: () -> Unit,
    onClickedCameraButton: () -> Unit,
    onClickMultipleSelectButton: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
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
                isEnabledMultipleSelectMode = isEnabledMultipleSelectMode,
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
    isEnabledMultipleSelectMode: Boolean,
    onClicked: () -> Unit
) {
    val containerColor =
        if (isEnabledMultipleSelectMode) Color.InstaBlue else Color.White.copy(alpha = 0.4f)
    val contentColor =
        if (isEnabledMultipleSelectMode) Color.White else MaterialTheme.colorScheme.onBackground
    IconButton(
        modifier = modifier,
        onClick = onClicked,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = containerColor,
            contentColor = contentColor
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
                permissionIsGranted = true,
                onClickedCameraButton = {},
                onCloseClick = {},
                onNextClick = {},
                uiState = PostGalleryUiState(
                    mediaAlbumNames = listOf("Album 1", "Album 2"),
                    selectedAlbumName = "Album 1",
                    urisInSelectedAlbum = listOf(),
                ),
                onEvent = {}
            )
        }
    }
}