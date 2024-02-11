package com.prmto.share_presentation.postGallery.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.prmto.core_domain.usecase.AlbumAndCoverImage

@Composable
fun AlbumAndCoverItem(
    modifier: Modifier = Modifier,
    albumAndCoverImage: AlbumAndCoverImage,
    onClickedItem: () -> Unit
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