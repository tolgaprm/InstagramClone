package com.prmto.core_presentation.components

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.prmto.core_domain.constants.UiText
import com.prmto.core_presentation.util.asString

@Composable
fun GalleryScreenBox(
    modifier: Modifier = Modifier,
    permissionIsGranted: Boolean,
    selectedImageUri: Uri?,
    urisInSelectedAlbum: List<Uri>,
    errorMessage: UiText? = null,
    onClickImageItem: (selectedUri: Uri) -> Unit,
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
                    model = selectedImageUri
                )

                addComposableOfTheCenter()

                LazyVerticalGrid(
                    modifier = Modifier.height(heightOfHalf),
                    columns = GridCells.Adaptive(100.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(urisInSelectedAlbum, key = { it }) { uri ->
                        ImageItemInDisabledMultipleSelection(
                            uri = uri,
                            onClickImageItem = onClickImageItem
                        )
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