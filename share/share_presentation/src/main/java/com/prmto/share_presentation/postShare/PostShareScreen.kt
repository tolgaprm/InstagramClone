package com.prmto.share_presentation.postShare

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.prmto.core_domain.util.toUris
import com.prmto.core_presentation.components.InstaButton
import com.prmto.core_presentation.components.InstaIconButton
import com.prmto.core_presentation.components.InstaProgressIndicator
import com.prmto.core_presentation.components.TextSelectionContainer
import com.prmto.core_presentation.navigation.Screen
import com.prmto.core_presentation.ui.HandleConsumableViewEvents
import com.prmto.core_presentation.ui.theme.InstaBlue
import com.prmto.share_presentation.R
import com.prmto.share_presentation.components.SharePostTopAppBar

@Composable
internal fun PostShareRoute(
    modifier: Modifier = Modifier,
    viewModel: PostShareViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit,
    onNavigateToPostPreview: (List<Uri>) -> Unit,
    onNavigateToHome: () -> Unit
) {
    val postShareUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val consumableEvents by viewModel.consumableViewEvents.collectAsStateWithLifecycle()

    PostShareScreen(
        modifier = modifier,
        postShareUiState = postShareUiState,
        onClickBackNavigation = onPopBackStack,
        onEvent = viewModel::onEvent,
        onNavigateToPostPreview = {
            onNavigateToPostPreview(
                postShareUiState.selectedPostImageUris.toUris()
            )
        }
    )

    HandleConsumableViewEvents(
        consumableViewEvents = consumableEvents,
        onEventNavigate = { route ->
            if (route == Screen.Home.route) {
                onNavigateToHome()
            }
        },
        onEventConsumed = viewModel::onEventConsumed
    )
}

@Composable
internal fun PostShareScreen(
    modifier: Modifier = Modifier,
    postShareUiState: PostShareUiState,
    onClickBackNavigation: () -> Unit,
    onEvent: (PostShareEvent) -> Unit,
    onNavigateToPostPreview: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SharePostTopAppBar(
                title = stringResource(id = R.string.new_post),
                navigationIcon = {
                    InstaIconButton(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                        onClick = onClickBackNavigation
                    )
                }
            )
        },
        bottomBar = {
            InstaButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                buttonText = stringResource(id = R.string.share),
                enabled = postShareUiState.isPostUploading.not(),
                onClick = { onEvent(PostShareEvent.OnPostShareClicked) },
            )
        }
    ) {
        if (postShareUiState.isPostUploading) {
            InstaProgressIndicator()
        }
        PostShareContent(
            modifier = Modifier.padding(paddingValues = it),
            caption = postShareUiState.caption,
            selectedPostImageUris = postShareUiState.selectedPostImageUris.toUris(),
            onCaptionChange = {
                onEvent(PostShareEvent.OnCaptionChanged(it))
            },
            onClickSmallPreview = onNavigateToPostPreview
        )
    }
}

@Composable
private fun PostShareContent(
    modifier: Modifier = Modifier,
    caption: String,
    selectedPostImageUris: List<Uri>,
    onCaptionChange: (String) -> Unit,
    onClickSmallPreview: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = if (selectedPostImageUris.size == 1) {
                Arrangement.Center
            } else {
                Arrangement.spacedBy(8.dp)
            }
        ) {
            items(selectedPostImageUris) { uri ->
                PostImageSmallPreview(
                    postImageUri = uri,
                    onClick = onClickSmallPreview
                )
            }
        }

        TextSelectionContainer {
            CaptionTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                caption = caption,
                onCaptionChange = onCaptionChange,
                placeholder = stringResource(id = R.string.write_a_caption)
            )
        }
    }
}

@Composable
private fun CaptionTextField(
    modifier: Modifier = Modifier,
    caption: String,
    placeholder: String,
    onCaptionChange: (String) -> Unit
) {
    Box(modifier = modifier) {
        BasicTextField(
            value = caption,
            onValueChange = onCaptionChange,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            cursorBrush = SolidColor(Color.InstaBlue),
        )
        if (caption.isEmpty()) {
            Text(
                text = placeholder,
                color = Color.LightGray
            )
        }
    }
}

@Composable
private fun PostImageSmallPreview(
    modifier: Modifier = Modifier,
    postImageUri: Uri,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(250.dp)
            .clickable(
                onClick = onClick
            )
    ) {
        AsyncImage(
            model = postImageUri,
            contentDescription = stringResource(id = R.string.post_image),
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
    }
}