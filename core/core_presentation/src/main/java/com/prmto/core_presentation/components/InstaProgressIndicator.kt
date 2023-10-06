package com.prmto.core_presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.prmto.core_presentation.R
import com.prmto.core_presentation.ui.theme.InstaBlue

@Composable
fun InstaProgressIndicator(
    modifier: Modifier = Modifier,
) {
    val contentDescriptionOf = stringResource(R.string.progress_indicator)
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.semantics {
                contentDescription = contentDescriptionOf
            },
            color = Color.InstaBlue,
        )
    }
}