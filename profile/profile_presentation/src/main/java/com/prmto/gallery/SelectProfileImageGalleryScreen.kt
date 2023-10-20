package com.prmto.gallery

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prmto.camera.crop.CropActivityResultContract
import com.prmto.common.components.ProfileTopBar
import com.prmto.core_presentation.components.GalleryDropDownButton
import com.prmto.core_presentation.components.GalleryScreenBox
import com.prmto.core_presentation.previews.UiModePreview
import com.prmto.core_presentation.ui.theme.InstaBlue
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme
import com.prmto.profile_presentation.R
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
internal fun ProfileImageGalleryScreen(
    modifier: Modifier = Modifier,
    uiState: SelectProfileImageGalleryUiState,
    permissionIsGranted: Boolean,
    onPopBackStack: () -> Unit,
    onEvent: (SelectProfileImageGalleryEvent) -> Unit,
    handlePermission: @Composable () -> Unit = {},
) {
    val cropActivityLauncher =
        rememberLauncherForActivityResult(
            contract = CropActivityResultContract(isCircle = true),
            onResult = { croppedImage ->
                croppedImage?.let {
                    onEvent(
                        SelectProfileImageGalleryEvent.CropImage(
                            croppedImage = croppedImage
                        )
                    )
                }
            }
        )
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = false)
    )
    val context = LocalContext.current
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        modifier = modifier.fillMaxSize(),
        topBar = {
            ProfileTopBar(
                onPopBackStack = onPopBackStack,
                titleComposable = {
                    GalleryDropDownButton(
                        selectedDirectoryName = uiState.selectedAlbumName,
                        onClicked = {
                            coroutineScope.launch {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            }
                        }
                    )
                },
                actions = {
                    TextButton(
                        onClick = {
                            uiState.selectedImageUri?.let {
                                cropActivityLauncher.launch(it)
                            } ?: run {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.please_select_an_image),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.next),
                            color = Color.InstaBlue
                        )
                    }
                }
            )
        },
        sheetContent = {
            SheetContent(
                albumNames = uiState.mediaAlbumNames,
                onnClickedAlbum = { albumName ->
                    onEvent(
                        SelectProfileImageGalleryEvent.SelectAlbum(albumName = albumName)
                    )

                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.hide()
                    }
                }
            )
        }
    )
    {
        GalleryScreenBox(
            permissionIsGranted = permissionIsGranted,
            selectedImageUri = uiState.selectedImageUri,
            urisInSelectedAlbum = uiState.urisInSelectedAlbum,
            onClickImageItem = { uri ->
                onEvent(
                    SelectProfileImageGalleryEvent.SelectImage(
                        uri = uri
                    )
                )
            },
            errorMessage = uiState.errorMessage,
            handlePermission = handlePermission
        )
    }
}

@Composable
private fun SheetContent(
    modifier: Modifier = Modifier,
    albumNames: List<String>,
    onnClickedAlbum: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp), modifier = modifier
    ) {
        items(albumNames, key = { it }) { albumName ->
            AlbumNameItem(
                albumName = albumName,
                onClicked = { onnClickedAlbum(albumName) }
            )
        }
    }
}

@Composable
fun AlbumNameItem(
    albumName: String,
    onClicked: () -> Unit
) {
    Text(
        text = albumName,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClicked() }
            .padding(8.dp),
        style = MaterialTheme.typography.bodyLarge
    )
}

@UiModePreview
@Composable
fun ProfileImageGalleryScreenPreview() {
    InstagramCloneTheme {
        Surface {
            ProfileImageGalleryScreen(
                uiState = SelectProfileImageGalleryUiState(),
                onPopBackStack = {},
                onEvent = { },
                permissionIsGranted = true
            )
        }
    }
}