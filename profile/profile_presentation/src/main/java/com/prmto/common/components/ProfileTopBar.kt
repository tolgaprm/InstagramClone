package com.prmto.common.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.prmto.core_presentation.previews.UiModePreview
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme
import com.prmto.profile_presentation.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ProfileTopBar(
    onPopBackStack: () -> Unit,
    titleText: String? = null,
    actions: @Composable RowScope.() -> Unit = {},
    titleComposable: @Composable () -> Unit = {},
) {
    TopAppBar(
        title = if (titleText != null) {
            { Text(text = titleText) }
        } else {
            titleComposable
        },
        navigationIcon = {
            IconButton(onClick = { onPopBackStack() }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close)
                )
            }
        },
        actions = actions
    )
}

@UiModePreview
@Composable
fun ProfileTopBarPreview() {
    InstagramCloneTheme {
        Surface {
            ProfileTopBar(
                titleText = "Title",
                onPopBackStack = {}
            )
        }
    }
}