package com.prmto.setting_presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.prmto.core_presentation.ui.theme.InstaBlue
import com.prmto.profile_presentation.R
import com.prmto.setting_presentation.components.SettingItem
import com.prmto.setting_presentation.components.SettingSection
import com.prmto.setting_presentation.event.SettingScreenEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    uiState: SettingScreenUiState,
    onNavigateBack: () -> Unit,
    onNavigateToEditProfile: () -> Unit,
    onEvent: (SettingScreenEvent) -> Unit
) {
    var isShowAlertDialog by remember { mutableStateOf(false) }
    Scaffold(topBar = {
        TopAppBar(modifier = Modifier.shadow(elevation = 8.dp), title = {
            Text(text = stringResource(R.string.settings))
        }, navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = null
                )
            }
        })
    }) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SettingSection(
                    sectionName = stringResource(R.string.account_settings)
                ) {
                    SettingItem(
                        imageVector = Icons.Outlined.Edit,
                        settingName = stringResource(id = R.string.edit_profile),
                        onClickSettingItem = onNavigateToEditProfile
                    )

                    SettingItem(imageVector = ImageVector.vectorResource(id = R.drawable.outline_password_24),
                        settingName = stringResource(R.string.change_password),
                        onClickSettingItem = { })

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 32.dp),
                    ) {
                        Row {
                            SettingItem(
                                imageVector = Icons.Outlined.Lock,
                                settingName = stringResource(R.string.private_account),
                            )
                        }

                        Switch(
                            checked = true,
                            onCheckedChange = {

                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.InstaBlue,
                                checkedTrackColor = Color.InstaBlue.copy(alpha = 0.5f),
                                uncheckedThumbColor = Color.LightGray,
                                uncheckedTrackColor = Color.LightGray.copy(alpha = 0.5f),
                            ),
                        )
                    }
                }

                Divider(modifier = Modifier.padding(horizontal = 16.dp))

                SettingSection(
                    sectionName = stringResource(R.string.other_settings)
                ) {
                    SettingItem(imageVector = ImageVector.vectorResource(com.prmto.core_presentation.R.drawable.save),
                        settingName = stringResource(R.string.saved),
                        onClickSettingItem = {})

                    SettingItem(imageVector = Icons.Outlined.FavoriteBorder,
                        settingName = stringResource(R.string.liked_your_posts),
                        onClickSettingItem = { })

                    SettingItem(imageVector = Icons.Outlined.Delete,
                        settingName = stringResource(R.string.clear_the_search_history),
                        textColor = Color.InstaBlue,
                        onClickSettingItem = { })
                }

                Divider(modifier = Modifier.padding(horizontal = 16.dp))

                SettingSection(sectionName = stringResource(R.string.login)) {
                    SettingItem(imageVector = Icons.Outlined.ExitToApp,
                        settingName = stringResource(R.string.logout),
                        textColor = Color.InstaBlue,
                        onClickSettingItem = {
                            isShowAlertDialog = true
                        })
                }
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(alignment = Alignment.Center),
                    color = Color.InstaBlue
                )
            }
        }

        if (isShowAlertDialog) {
            AlertDialog(onDismissRequest = {
                isShowAlertDialog = false
            }, confirmButton = {
                Button(onClick = {
                    onEvent(SettingScreenEvent.Logout)
                    isShowAlertDialog = false
                }) {
                    Text(
                        text = stringResource(id = R.string.yes),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.InstaBlue
                    )
                }
            }, dismissButton = {
                Button(onClick = { isShowAlertDialog = false }) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }, title = {
                Text(
                    text = stringResource(R.string.logout),
                    style = MaterialTheme.typography.titleMedium
                )
            }, text = {
                Text(
                    text = stringResource(R.string.logout_message),
                    style = MaterialTheme.typography.bodyMedium
                )
            })
        }
    }
}
