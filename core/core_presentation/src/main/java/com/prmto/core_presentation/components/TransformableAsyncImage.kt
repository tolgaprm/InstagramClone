package com.prmto.core_presentation.components

import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AspectRatio
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.prmto.core_presentation.R

@Composable
fun TransformableAsyncImage(
    modifier: Modifier = Modifier,
    model: Any?,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit
) {
    var scale by rememberSaveable { mutableFloatStateOf(1f) }
    var rotation by rememberSaveable { mutableFloatStateOf(0f) }
    var offset by rememberSaveable(stateSaver = OffsetSaver) { mutableStateOf(Offset.Zero) }

    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }

    LaunchedEffect(
        key1 = model
    ) {
        scale = 1f
        rotation = 0f
        offset = Offset.Zero
    }

    Box(
        modifier = modifier
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = rotation,
                    translationX = offset.x,
                    translationY = offset.y
                )
                // add transformable to listen to multitouch transformation events
                // after offset
                .transformable(state = state),
            model = model,
            contentDescription = contentDescription,
            contentScale = contentScale,
        )

        IconButton(
            modifier = Modifier.align(Alignment.BottomStart),
            onClick = {
                scale = 1f
                rotation = 0f
                offset = Offset.Zero
            },
        ) {
            Icon(
                imageVector = Icons.Default.AspectRatio,
                contentDescription = stringResource(R.string.reset_image_to_original)
            )
        }
    }
}

val OffsetSaver = run {
    mapSaver(
        save = { offset ->
            mapOf(
                "x" to offset.x,
                "y" to offset.y
            )
        },
        restore = {
            Offset(
                it["x"] as Float,
                it["y"] as Float
            )
        }
    )
}