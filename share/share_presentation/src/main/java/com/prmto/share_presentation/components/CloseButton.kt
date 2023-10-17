package com.prmto.share_presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prmto.share_presentation.R

@Composable
fun CloseButton(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    onCloseClick: () -> Unit
) {
    IconButton(onClick = onCloseClick) {
        Icon(
            modifier = modifier.size(32.dp),
            imageVector = Icons.Default.Close,
            contentDescription = stringResource(id = R.string.close),
            tint = color
        )
    }
}