package com.prmto.edit_profile_presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prmto.core_domain.model.UserDetail
import com.prmto.core_presentation.components.CircleProfileImage
import com.prmto.core_presentation.ui.theme.InstaBlue
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme
import com.prmto.core_presentation.ui.theme.colorBlur
import com.prmto.edit_profile_presentation.event.EditProfileUiEvent
import com.prmto.edit_profile_presentation.navigation.components.EditProfileSection
import com.prmto.edit_profile_presentation.navigation.components.EditProfileTopBar
import com.prmto.profile_presentation.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    uiState: EditProfileUiState,
    onPopBackStack: () -> Unit,
    onNavigateToProfileCamera: () -> Unit,
    onNavigateToGallery: () -> Unit,
    onEvent: (EditProfileUiEvent) -> Unit
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState()
    )
    val coroutineScope = rememberCoroutineScope()
    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContainerColor = colorBlur(),
        topBar = {
            EditProfileTopBar(isShowSaveButton = uiState.isShowSaveButton,
                onClickClose = onPopBackStack,
                onClickSave = {
                    onEvent(EditProfileUiEvent.UpdateProfileInfo)
                })
        },
        sheetContent = {
            BottomSheetContent(
                onClickCamera = { onNavigateToProfileCamera() },
                onClickGallery = { onNavigateToGallery() }
            )
        },
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            EditProfileContent(updatedUserDetail = uiState.updatedUserDetail,
                onEvent = onEvent,
                onClickChangeProfilePhoto = {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    }
                })

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center), color = Color.InstaBlue
                )
            }
        }
    }
}

@Composable
private fun EditProfileContent(
    updatedUserDetail: UserDetail,
    onEvent: (EditProfileUiEvent) -> Unit,
    onClickChangeProfilePhoto: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircleProfileImage(
            imageUrl = updatedUserDetail.profilePictureUrl
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onClickChangeProfilePhoto()
            }
            .padding(8.dp),
            text = stringResource(R.string.change_profile_photo),
            color = Color.InstaBlue,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold))
        EditProfileSection(sectionName = stringResource(R.string.edit_profile_name_section),
            value = updatedUserDetail.name,
            onValueChange = {
                onEvent(EditProfileUiEvent.EnteredName(it))
            })

        EditProfileSection(sectionName = stringResource(R.string.edit_profile_username),
            value = updatedUserDetail.username,
            onValueChange = {
                onEvent(EditProfileUiEvent.EnteredUsername(it))
            })

        EditProfileSection(sectionName = stringResource(R.string.edit_profile_bio),
            value = updatedUserDetail.bio,
            onValueChange = {
                onEvent(EditProfileUiEvent.EnteredBio(it))
            })

        EditProfileSection(sectionName = stringResource(R.string.edit_profile_website),
            value = updatedUserDetail.webSite,
            onValueChange = {
                onEvent(EditProfileUiEvent.EnteredWebsite(it))
            })
    }
}

@Composable
private fun BottomSheetContent(
    onClickCamera: () -> Unit,
    onClickGallery: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomSheetItem(
            modifier = Modifier.clickable { onClickCamera() },
            icon = Icons.Default.PhotoCamera,
            text = stringResource(R.string.take_a_photo_from_camera)
        )

        BottomSheetItem(
            modifier = Modifier.clickable { onClickGallery() },
            icon = Icons.Default.Image,
            text = stringResource(R.string.choose_a_photo_from_gallery)
        )
    }
}

@Composable
private fun BottomSheetItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            imageVector = icon,
            contentDescription = text,
            tint = Color.White
        )
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Preview
@Composable
fun EditProfilePreview() {
    InstagramCloneTheme {
        EditProfileScreen(
            uiState = EditProfileUiState(),
            onPopBackStack = {},
            onEvent = {},
            onNavigateToProfileCamera = {},
            onNavigateToGallery = {}
        )
    }
}