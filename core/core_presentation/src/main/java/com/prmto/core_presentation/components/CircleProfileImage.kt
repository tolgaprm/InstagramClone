package com.prmto.core_presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.prmto.core_presentation.R
import com.prmto.core_presentation.previews.UiModePreview
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme

@Composable
fun CircleProfileImage(
    modifier: Modifier = Modifier,
    imageUrl: Any?,
    imageSize: Dp = 96.dp,
    borderWidth: Dp = 3.dp
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .error(R.drawable.account)
            .build(),
        contentDescription = null,
        modifier = modifier
            .size(imageSize)
            .clip(CircleShape)
            .border(
                width = borderWidth,
                color = Color.LightGray,
                shape = CircleShape
            ),
        contentScale = ContentScale.Crop
    )
}

@UiModePreview
@Composable
fun CircleProfileImagePreview() {
    InstagramCloneTheme {
        Surface {
            CircleProfileImage(imageUrl = "")
        }
    }
}