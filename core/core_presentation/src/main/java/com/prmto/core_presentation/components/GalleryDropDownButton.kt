package com.prmto.core_presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GalleryDropDownButton(
    modifier: Modifier = Modifier,
    selectedDirectoryName: String,
    onClicked: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onClicked() }) {
        Text(
            text = selectedDirectoryName, maxLines = 1
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null
        )
    }
}