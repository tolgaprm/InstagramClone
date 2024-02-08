package com.prmto.share_presentation.postGallery.components

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prmto.core_domain.usecase.AlbumAndCoverImage
import com.prmto.core_presentation.previews.UiModePreview
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme
import com.prmto.share_presentation.R

val postGallerySheetContentTestTag = "postGallerySheetContent"

@Composable
fun PostGallerySheetContent(
    modifier: Modifier = Modifier,
    albumsAndCoverImage: List<AlbumAndCoverImage>,
    onClickedAlbumItem: (albumName: String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag(postGallerySheetContentTestTag)
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
                    AlbumAndCoverItem(
                        albumAndCoverImage = it,
                        onClickedItem = { onClickedAlbumItem(it.albumName) }
                    )
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
        CommonAlbumButton(
            title = stringResource(R.string.favorites),
            icon = Icons.Outlined.FavoriteBorder,
            contentDescription = stringResource(R.string.favorites),
            onClicked = {}
        )

        Spacer(modifier = Modifier.width(16.dp))

        CommonAlbumButton(
            title = stringResource(R.string.videos),
            icon = Icons.Outlined.PlayCircle,
            contentDescription = stringResource(R.string.videos),
            onClicked = {}
        )
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

@UiModePreview
@Composable
fun BottomSheetPreview() {
    InstagramCloneTheme {
        Surface {
            PostGallerySheetContent(
                albumsAndCoverImage = listOf(
                    AlbumAndCoverImage(
                        albumName = "Album 1",
                        firstImageUri = Uri.parse("https://picsum.photos/200/300")
                    ),
                    AlbumAndCoverImage(
                        albumName = "Album 2",
                        firstImageUri = Uri.parse("https://picsum.photos/200/300")
                    ),
                    AlbumAndCoverImage(
                        albumName = "Album 3",
                        firstImageUri = Uri.parse("https://picsum.photos/200/300")
                    ),
                    AlbumAndCoverImage(
                        albumName = "Album 4",
                        firstImageUri = Uri.parse("https://picsum.photos/200/300")
                    ),
                ),
                onClickedAlbumItem = {}
            )
        }
    }
}