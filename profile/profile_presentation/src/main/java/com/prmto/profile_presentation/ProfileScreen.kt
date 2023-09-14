package com.prmto.profile_presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prmto.core_presentation.ui.theme.InstaBlue
import com.prmto.profile_presentation.components.AccountInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onNavigateToSettingScreen: () -> Unit,
    onNavigateToEditProfileScreen: () -> Unit
) {
    val userData = uiState.userData
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = userData.userDetail.username,
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                actions = {
                    if (uiState.isOwnProfile) {
                        IconButton(onClick = onNavigateToSettingScreen) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(R.string.menu)
                            )
                        }
                    }
                }
            )
        },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                AccountInfo(
                    userDetail = userData.userDetail,
                    statistics = userData.statistics,
                    modifier = Modifier.padding(it)
                )
                if (uiState.isOwnProfile) {
                    EditProfileButton(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        onClick = {
                            onNavigateToEditProfileScreen()
                        }
                    )
                }
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(alignment = Alignment.Center),
                    color = Color.InstaBlue
                )
            }
        }
    }
}

@Composable
fun EditProfileButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .height(35.dp)
            .border(
                width = 1.dp,
                color = Color.LightGray.copy(alpha = 0.5f),
                shape = RoundedCornerShape(6.dp)
            )
            .clip(RoundedCornerShape(6.dp)),
        onClick = onClick
    ) {
        Text(text = stringResource(id = R.string.edit_profile))
    }
}