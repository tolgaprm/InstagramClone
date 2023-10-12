package com.prmto.profile_presentation

import android.content.Context
import android.content.Intent
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.prmto.core_presentation.previews.UiModePreview
import com.prmto.core_presentation.ui.HandleConsumableViewEvents
import com.prmto.core_presentation.ui.theme.InstaBlue
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme
import com.prmto.profile_presentation.components.AccountInfo
import com.prmto.profile_presentation.previewDataProvider.ProfileUiStatePreviewProvider

@Composable
internal fun ProfileRoute(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateToSettingScreen: () -> Unit,
    onNavigateToEditProfileScreen: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val consumableViewEvents by viewModel.consumableViewEvents.collectAsStateWithLifecycle()
    ProfileScreen(
        modifier = modifier,
        uiState = uiState,
        onNavigateToSettingScreen = onNavigateToSettingScreen,
        onNavigateToEditProfileScreen = onNavigateToEditProfileScreen
    )

    LaunchedEffect(key1 = true) {
        if (uiState.isOwnProfile)
            viewModel.getUserDataFromPreferences()
    }

    HandleConsumableViewEvents(
        consumableViewEvents = consumableViewEvents,
        onEventNavigate = { },
        onEventConsumed = viewModel::onEventConsumed
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProfileScreen(
    modifier: Modifier = Modifier,
    uiState: ProfileUiState,
    onNavigateToSettingScreen: () -> Unit,
    onNavigateToEditProfileScreen: () -> Unit
) {
    val userData = uiState.userData
    val context = LocalContext.current
    Scaffold(
        modifier = modifier.fillMaxSize(),
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
                    modifier = Modifier.padding(it),
                    onWebSiteTextClick = {
                        intentToWebSite(
                            url = userData.userDetail.webSite,
                            context = context
                        )
                    }
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

private fun intentToWebSite(url: String, context: Context) {
    Intent().apply {
        data = android.net.Uri.parse(url)
        action = Intent.ACTION_VIEW
    }.also {
        startActivity(context, it, null)
    }
}

@UiModePreview
@Composable
fun ProfileScreenPreview(
    @PreviewParameter(ProfileUiStatePreviewProvider::class) uiState: ProfileUiState
) {
    InstagramCloneTheme {
        Surface {
            ProfileScreen(
                uiState = uiState,
                onNavigateToSettingScreen = { }
            ) { }
        }
    }
}