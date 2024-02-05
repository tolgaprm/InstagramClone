package com.prmto.share_presentation.postPreview

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.prmto.share_presentation.R
import com.prmto.share_presentation.components.CloseButton
import com.prmto.share_presentation.components.SharePostTopAppBar

@Composable
internal fun PostPreviewRoute(
    modifier: Modifier = Modifier,
    onPopBackStack: () -> Unit
) {
    val viewModel: PostPreviewViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PostPreviewScreen(
        modifier = modifier,
        postPreviewPhotoUris = uiState.postPreviewPhotoUris,
        onCloseClick = onPopBackStack
    )
}

@Composable
internal fun PostPreviewScreen(
    modifier: Modifier = Modifier,
    postPreviewPhotoUris: List<Uri>,
    onCloseClick: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SharePostTopAppBar(
                title = stringResource(R.string.preview),
                navigationIcon = {
                    CloseButton(
                        onCloseClick = onCloseClick
                    )
                }
            )
        }
    ) {
        PostPreviewContent(
            modifier = Modifier.padding(it),
            postPreviewPhotoUris = postPreviewPhotoUris
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PostPreviewContent(
    modifier: Modifier = Modifier,
    postPreviewPhotoUris: List<Uri>
) {
    val pagerState = rememberPagerState { postPreviewPhotoUris.size }
    BoxWithConstraints(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val height = remember(this.maxHeight) { maxHeight / 2 }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(height),
        ) {
            AsyncImage(
                model = postPreviewPhotoUris[it],
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
    }
}