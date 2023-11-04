package com.prmto.share_presentation.postGallery

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.PlayCircle
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.prmto.camera.crop.CropActivityResultContract
import com.prmto.core_domain.usecase.AlbumAndCoverImage
import com.prmto.core_presentation.components.GalleryDropDownButton
import com.prmto.core_presentation.components.GalleryScreenBoxInPostGallery
import com.prmto.core_presentation.previews.UiModePreview
import com.prmto.core_presentation.ui.HandleConsumableViewEvents
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
    modifier: Modifier = Modifier, onNavigateToPostCamera: () -> Unit, onPopBackStack: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: PostGalleryViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val consumableViewEvents by viewModel.consumableViewEvents.collectAsStateWithLifecycle()
    val permissionState = handleFilePermissionAccess(onPermissionGranted = {
        viewModel.onEvent(PostGalleryEvent.AllPermissionGranted)
    })
    val permissionProvider = getPermissionInfoProvider(permissionToRequestForFile())
    val cropActivityLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(),
        onResult = { croppedUri ->
            croppedUri?.let {
                viewModel.onEvent(PostGalleryEvent.OnImageCropped(it))
            }
        })
    val warningMessage = stringResource(R.string.please_you_select_an_image)

    PostGalleryScreen(modifier = modifier,
        uiState = uiState,
        permissionIsGranted = permissionState.status.isGranted,
        onClickedCameraButton = onNavigateToPostCamera,
        onCloseClick = onPopBackStack,
        onNextClick = {
            handleOnNextClick(context = context,
                isEnabledMultipleSelectMode = uiState.isActiveMultipleSelection,
                selectedImageUri = uiState.selectedImageUri,
                selectedUrisInEnabledMultipleSelectMode = uiState.selectedUrisInEnabledMultipleSelectMode,
                warningMessage = warningMessage,
                onLaunchCropActivity = { uri -> cropActivityLauncher.launch(uri) })
        },
        onEvent = viewModel::onEvent,
        handlePermission = {
            HandlePermissionStatus(
                permissionState = permissionState, permissionProvider = permissionProvider
            )
        })

    HandleConsumableViewEvents(
        consumableViewEvents = consumableViewEvents,
        onEventNavigate = {},
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
    BottomSheetScaffold(scaffoldState = bottomSheetScaffoldState, sheetContent = {
        PostGallerySheetContent(albumsAndCoverImage = uiState.albumAndCoverImages,
            onClickedAlbumItem = {
                onEvent(PostGalleryEvent.OnClickAlbumItem(it))
                coroutineScope.launch {
                    bottomSheetScaffoldState.bottomSheetState.hide()
                }
            })
    }, topBar = {
        PostGalleryTopSection(
            onCloseClick = onCloseClick, onNextClick = onNextClick
        )
    }, content = {
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
                PostGalleryCenterSection(selectedDirectoryName = uiState.selectedAlbumName,
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
                    })
            },
            handlePermission = handlePermission
        )
    })
}

@Composable
private fun PostGalleryTopSection(
    modifier: Modifier = Modifier, onCloseClick: () -> Unit, onNextClick: () -> Unit
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
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
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
            selectedDirectoryName = selectedDirectoryName, onClicked = onClickedDropDown
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            MultipleSelectedButton(
                isEnabledMultipleSelectMode = isEnabledMultipleSelectMode,
                onClicked = onClickMultipleSelectButton
            )
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(
                onClick = onClickedCameraButton, colors = IconButtonDefaults.iconButtonColors(
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
    modifier: Modifier = Modifier, isEnabledMultipleSelectMode: Boolean, onClicked: () -> Unit
) {
    val containerColor =
        if (isEnabledMultipleSelectMode) Color.InstaBlue else Color.White.copy(alpha = 0.4f)
    val contentColor = if (isEnabledMultipleSelectMode) Color.White else Color.Black
    IconButton(
        modifier = modifier, onClick = onClicked, colors = IconButtonDefaults.iconButtonColors(
            containerColor = containerColor, contentColor = contentColor
        )
    ) {
        Icon(
            imageVector = Icons.Default.ContentCopy,
            contentDescription = stringResource(R.string.select_multiple_images_or_videos)
        )
    }
}

@Composable
fun PostGallerySheetContent(
    modifier: Modifier = Modifier,
    albumsAndCoverImage: List<AlbumAndCoverImage>,
    onClickedAlbumItem: (albumName: String) -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.select_album),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        Column(
            modifier = Modifier.padding(
                horizontal = 16.dp, vertical = 16.dp
            )
        ) {
            CommonAlbumsSectionInSheetContent()

            Text(
                text = stringResource(R.string.albums),
                style = MaterialTheme.typography.labelSmall,
            )

            LazyHorizontalGrid(
                rows = GridCells.Adaptive(120.dp),
                modifier = Modifier.padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(albumsAndCoverImage, key = { it.albumName }) {
                    AlbumAndCoverItem(albumAndCoverImage = it,
                        onClickedItem = { onClickedAlbumItem(it.albumName) })
                }
            }
        }
    }
}

@Composable
private fun CommonAlbumsSectionInSheetContent(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CommonAlbumButton(title = stringResource(R.string.favorites),
            icon = Icons.Outlined.FavoriteBorder,
            contentDescription = stringResource(R.string.favorites),
            onClicked = {})

        Spacer(modifier = Modifier.width(16.dp))

        CommonAlbumButton(title = stringResource(R.string.videos),
            icon = Icons.Outlined.PlayCircle,
            contentDescription = stringResource(R.string.videos),
            onClicked = {})
    }
}

@Composable
private fun CommonAlbumButton(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    contentDescription: String,
    onClicked: () -> Unit
) {
    Column {
        IconButton(
            modifier = modifier.padding(8.dp),
            onClick = onClicked,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
            )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Text(text = title)
    }
}

@Composable
fun AlbumAndCoverItem(
    modifier: Modifier = Modifier, albumAndCoverImage: AlbumAndCoverImage, onClickedItem: () -> Unit
) {
    Column(
        modifier = modifier
            .size(140.dp)
            .clickable { onClickedItem() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp)),
            model = albumAndCoverImage.firstImageUri,
            contentDescription = albumAndCoverImage.albumName,
            contentScale = ContentScale.Crop
        )
        Text(
            text = albumAndCoverImage.albumName,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@UiModePreview
@Composable
fun PostGalleryScreenPreview() {
    InstagramCloneTheme {
        Surface {
            PostGalleryScreen(onClickedCameraButton = {},
                onCloseClick = {},
                onNextClick = {},
                uiState = PostGalleryUiState(),
                onEvent = {})
        }
    }
}

@UiModePreview
@Composable
fun BottomSheetPreview() {
    InstagramCloneTheme {
        Surface {
            PostGallerySheetContent(albumsAndCoverImage = emptyList(), onClickedAlbumItem = {})
        }
    }
}