package com.prmto.gallery

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.prmto.common.components.ProfileTopBar
import com.prmto.core_presentation.components.TransformableAsyncImage
import com.prmto.core_presentation.previews.UiModePreview
import com.prmto.core_presentation.ui.theme.InstaBlue
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme
import com.prmto.core_presentation.util.asString
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
                    ProfileImageTopBarTitleSection(
                        selectedDirectoryName = uiState.selectedAlbumName,
                        onClicked = {
                            coroutineScope.launch {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            }
                        }
                    )
                },
                actions = {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(
                            text = stringResource(R.string.next), color = Color.InstaBlue
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
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val heightOfHalf = maxHeight / 2
            if (permissionIsGranted) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TransformableAsyncImage(
                        modifier = Modifier.height(heightOfHalf),
                        model = uiState.selectedImageUri
                    )

                    LazyVerticalGrid(
                        modifier = Modifier.height(heightOfHalf),
                        columns = GridCells.Adaptive(100.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(uiState.urisInSelectedAlbum, key = { it }) { uri ->
                            AsyncImage(
                                modifier = Modifier
                                    .size(120.dp)
                                    .clickable {
                                        onEvent(
                                            SelectProfileImageGalleryEvent.SelectImage(
                                                uri = uri
                                            )
                                        )
                                    },
                                model = uri,
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            } else {
                handlePermission()
            }

            if (uiState.errorMessage != null) {
                Toast.makeText(
                    context,
                    uiState.errorMessage.asString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

@Composable
fun ProfileImageTopBarTitleSection(
    selectedDirectoryName: String, onClicked: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClicked() }) {
        Text(
            text = selectedDirectoryName, maxLines = 1
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null
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