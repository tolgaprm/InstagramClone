package com.prmto.edit_profile_presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.prmto.core_presentation.components.CircleProfileImage
import com.prmto.core_presentation.ui.theme.InstaBlue
import com.prmto.edit_profile_presentation.event.EditProfileUiEvent
import com.prmto.edit_profile_presentation.navigation.components.EditProfileSection
import com.prmto.edit_profile_presentation.navigation.components.EditProfileTopBar
import com.prmto.profile_presentation.R

@Composable
fun EditProfileScreen(
    uiState: EditProfileUiState,
    onPopBackStack: () -> Unit,
    onEvent: (EditProfileUiEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            EditProfileTopBar(
                isShowSaveButton = uiState.isShowSaveButton,
                onClickClose = onPopBackStack,
                onClickSave = {
                    onEvent(EditProfileUiEvent.UpdateProfileInfo)
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircleProfileImage(
                    imageUrl = uiState.updatedUserDetail.profilePictureUrl
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {}
                        .padding(8.dp),
                    text = stringResource(R.string.change_profile_photo),
                    color = Color.InstaBlue,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                )
                EditProfileSection(
                    sectionName = stringResource(R.string.edit_profile_name_section),
                    value = uiState.updatedUserDetail.name,
                    onValueChange = {
                        onEvent(EditProfileUiEvent.EnteredName(it))
                    }
                )

                EditProfileSection(
                    sectionName = stringResource(R.string.edit_profile_username),
                    value = uiState.updatedUserDetail.username,
                    onValueChange = {
                        onEvent(EditProfileUiEvent.EnteredUsername(it))
                    }
                )

                EditProfileSection(
                    sectionName = stringResource(R.string.edit_profile_bio),
                    value = uiState.updatedUserDetail.bio,
                    onValueChange = {
                        onEvent(EditProfileUiEvent.EnteredBio(it))
                    }
                )

                EditProfileSection(
                    sectionName = stringResource(R.string.edit_profile_website),
                    value = uiState.updatedUserDetail.webSite,
                    onValueChange = {
                        onEvent(EditProfileUiEvent.EnteredWebsite(it))
                    }
                )
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.InstaBlue
                )
            }
        }
    }
}