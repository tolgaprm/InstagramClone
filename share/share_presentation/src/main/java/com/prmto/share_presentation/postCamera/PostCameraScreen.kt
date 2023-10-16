package com.prmto.share_presentation.postCamera

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FlipCameraAndroid
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.prmto.camera.InstaCamera
import com.prmto.camera.components.CameraFlashModeButton
import com.prmto.camera.crop.CropActivityResultContract
import com.prmto.camera.rememberCameraControllerWithImageCapture
import com.prmto.camera.util.CameraFlashMode
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme
import com.prmto.share_presentation.R

@Composable
internal fun PostCameraRoute(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit,
    onNavigateToPostGallery: () -> Unit
) {
    val viewModel: PostCameraViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cameraController = rememberCameraControllerWithImageCapture()
    val cropLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(),
        onResult = { uri ->
            uri?.let {
                viewModel.onEvent(PostCameraEvent.OnCroppedImage(it))
            }
        }
    )
    PostCameraScreen(
        modifier = modifier,
        uiState = uiState,
        onStartCamera = cameraController::startCamera,
        onTakePhoto = {
            cameraController.takePhoto(
                onPhotoCaptured = { uri ->
                    cropLauncher.launch(uri)
                }
            )
        },
        onClickChangeCamera = cameraController::changeCamera,
        onEvent = viewModel::onEvent,
        onClickClose = onNavigateToHome,
        onClickGallery = onNavigateToPostGallery
    )
}

@Composable
internal fun PostCameraScreen(
    modifier: Modifier = Modifier,
    uiState: PostCameraUiState,
    onStartCamera: (PreviewView) -> Unit,
    onTakePhoto: () -> Unit,
    onClickChangeCamera: () -> Unit,
    onClickClose: () -> Unit,
    onClickGallery: () -> Unit,
    onEvent: (PostCameraEvent) -> Unit,
) {
    InstaCamera(
        onStartCamera = onStartCamera,
        modifier = Modifier.fillMaxSize()
    )

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        PostCameraTopSection(
            modifier = Modifier
                .align(Alignment.TopCenter),
            flashMode = uiState.cameraFlashMode,
            onCloseClick = onClickClose,
            onClickFlashMode = { onEvent(PostCameraEvent.OnClickCameraFlashMode) }) {
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color.Black.copy(alpha = 0.6f)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CameraButton(
                onTakePhoto = onTakePhoto,
                modifier = Modifier.padding(32.dp)
            )

            PostCameraBottomSection(
                lastPhotoInGallery = uiState.lastPhotoInGallery,
                cameraTab = uiState.cameraTab,
                onClickGallery = onClickGallery,
                onClickChangeCamera = onClickChangeCamera
            )
        }
    }
}

@Composable
private fun PostCameraTopSection(
    modifier: Modifier = Modifier,
    flashMode: CameraFlashMode,
    onCloseClick: () -> Unit,
    onClickFlashMode: () -> Unit,
    onClickSetting: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.1f)),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onCloseClick) {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(id = R.string.close),
                tint = Color.White
            )
        }

        CameraFlashModeButton(
            cameraFlashMode = flashMode,
            onClickFlashMode = onClickFlashMode,
            tint = Color.White
        )

        IconButton(onClick = onClickSetting) {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.Default.Settings,
                contentDescription = stringResource(id = R.string.settings),
                tint = Color.White
            )
        }
    }
}

@Composable
private fun PostCameraBottomSection(
    modifier: Modifier = Modifier,
    lastPhotoInGallery: Uri? = null,
    cameraTab: CameraTab,
    onClickGallery: () -> Unit,
    onClickChangeCamera: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        GalleryButton(
            lastPhotoInGallery = lastPhotoInGallery,
            onClickGallery = onClickGallery
        )

        Text(
            text = stringResource(id = cameraTab.titleResId),
            style = MaterialTheme.typography.titleSmall,
            color = Color.White
        )

        ChangeCameraButton(onClick = onClickChangeCamera)
    }
}


@Composable
fun ChangeCameraButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            imageVector = Icons.Default.FlipCameraAndroid,
            contentDescription = stringResource(R.string.change_camera),
            tint = Color.White
        )
    }
}

@Composable
private fun GalleryButton(
    modifier: Modifier = Modifier,
    lastPhotoInGallery: Uri?,
    onClickGallery: () -> Unit
) {
    val galleryModifier = modifier
        .size(40.dp)
        .clip(RoundedCornerShape(4.dp))
        .clickable(onClick = onClickGallery)

    if (lastPhotoInGallery == null) {
        Surface(
            modifier = galleryModifier,
            color = Color.White.copy(alpha = 0.4f)
        ) {

        }
    } else {
        AsyncImage(
            modifier = galleryModifier,
            model = lastPhotoInGallery,
            contentDescription = null
        )
    }
}

@Composable
private fun CameraButton(
    modifier: Modifier = Modifier,
    onTakePhoto: () -> Unit
) {
    Surface(
        modifier = modifier
            .size(70.dp)
            .clip(CircleShape)
            .padding(2.dp)
            .border(2.dp, Color.Black, CircleShape)
            .clickable(onClick = onTakePhoto),
        color = Color.White
    ) {

    }
}

@Preview
@Composable
fun PostCameraScreenPreview() {
    InstagramCloneTheme {
        Surface {
            PostCameraScreen(
                uiState = PostCameraUiState(),
                onStartCamera = {},
                onTakePhoto = {},
                onClickChangeCamera = {},
                onClickClose = {},
                onClickGallery = {},
                onEvent = {},
            )
        }
    }
}