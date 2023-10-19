package com.prmto.core_presentation.components

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.prmto.core_domain.constants.UiText
import com.prmto.core_presentation.R
import com.prmto.core_presentation.ui.theme.InstaBlue
import com.prmto.core_presentation.util.asString

@Composable
fun GalleryScreenBoxInPostGallery(
    modifier: Modifier = Modifier,
    permissionIsGranted: Boolean,
    selectedImageUriShowOnTransformableImage: Uri?,
    selectedUrisInEnabledMultipleSelectMode: List<Uri>,
    urisInSelectedAlbum: List<Uri>,
    errorMessage: UiText? = null,
    onClickImageItem: (selectedUri: Uri) -> Unit,
    isEnableMultipleSelection: Boolean = false,
    handlePermission: @Composable () -> Unit,
    addComposableOfTheCenter: @Composable () -> Unit = {}
) {
    val context = LocalContext.current
    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        val heightOfHalf = maxHeight / 2
        if (permissionIsGranted) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TransformableAsyncImage(
                    modifier = Modifier.height(heightOfHalf),
                    model = selectedImageUriShowOnTransformableImage
                )

                addComposableOfTheCenter()

                LazyVerticalGrid(
                    modifier = Modifier.height(heightOfHalf),
                    columns = GridCells.Adaptive(100.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(urisInSelectedAlbum, key = { uri -> uri }) { uri ->
                        if (isEnableMultipleSelection) {
                            ImageItemInEnabledMultipleSelection(
                                uri = uri,
                                index = selectedUrisInEnabledMultipleSelectMode.indexOf(uri),
                                selectedUrisInEnabledMultipleSelectMode = selectedUrisInEnabledMultipleSelectMode,
                                onClickImageItem = onClickImageItem
                            )
                        } else {
                            ImageItemInDisabledMultipleSelection(
                                uri = uri,
                                onClickImageItem = onClickImageItem
                            )
                        }
                    }
                }
            }
        } else {
            handlePermission()
        }

        if (errorMessage != null) {
            Toast.makeText(
                context,
                errorMessage.asString(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

@Composable
private fun ImageItemInEnabledMultipleSelection(
    modifier: Modifier = Modifier,
    uri: Uri,
    index: Int,
    selectedUrisInEnabledMultipleSelectMode: List<Uri>,
    onClickImageItem: (selectedUri: Uri) -> Unit
) {
    val context = LocalContext.current
    val defaultColor = Color.White.copy(alpha = 0.5f)
    val color = rememberSaveable(
        stateSaver = Saver(
            save = { defaultColor.toArgb() },
            restore = { Color(it) }
        )
    ) { mutableStateOf(defaultColor) }

    Box(
        modifier = modifier.size(120.dp)
    ) {
        ImageItemInDisabledMultipleSelection(
            uri = uri,
            onClickImageItem = {
                if (selectedUrisInEnabledMultipleSelectMode.size >= 10
                    && !selectedUrisInEnabledMultipleSelectMode.contains(uri)
                ) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.the_limit_is_10_photos_or_videos),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    color.value = when (color.value) {
                        defaultColor -> Color.InstaBlue
                        else -> defaultColor
                    }
                    onClickImageItem(it)
                }
            }
        )
        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 4.dp)
                .size(30.dp)
                .clip(CircleShape)
                .border(2.dp, Color.White, shape = CircleShape)
                .align(alignment = Alignment.TopEnd)
                .background(color = color.value),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${index + 1}",
                color = Color.White,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
fun ImageItemInDisabledMultipleSelection(
    modifier: Modifier = Modifier,
    uri: Uri,
    onClickImageItem: (selectedUri: Uri) -> Unit
) {
    AsyncImage(
        modifier = modifier
            .size(120.dp)
            .clickable {
                onClickImageItem(uri)
            },
        model = uri,
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}