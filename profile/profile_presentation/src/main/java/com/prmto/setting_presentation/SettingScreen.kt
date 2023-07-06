package com.prmto.setting_presentation

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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.shadow(elevation = 8.dp),
                title = {
                    Text(text = stringResource(R.string.settings))
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            SettingSection(
                sectionName = stringResource(R.string.account_settings)
            ) {
                SettingItem(
                    imageVector = Icons.Outlined.Edit,
                    settingName = stringResource(id = R.string.edit_profile),
                    onClickSettingItem = { }
                )

                SettingItem(
                    imageVector = ImageVector.vectorResource(id = R.drawable.outline_password_24),
                    settingName = stringResource(R.string.change_password),
                    onClickSettingItem = { }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 32.dp),
                ) {
                    Row {
                        SettingItem(
                            imageVector = Icons.Outlined.Lock,
                            settingName = "Private Account",
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
                SettingItem(
                    imageVector = ImageVector.vectorResource(com.prmto.core_presentation.R.drawable.save),
                    settingName = stringResource(R.string.saved),
                    onClickSettingItem = {}
                )

                SettingItem(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    settingName = stringResource(R.string.liked_your_posts),
                    onClickSettingItem = { }
                )

                SettingItem(
                    imageVector = Icons.Outlined.Delete,
                    settingName = stringResource(R.string.clear_the_search_history),
                    textColor = Color.InstaBlue,
                    onClickSettingItem = { }
                )
            }

            Divider(modifier = Modifier.padding(horizontal = 16.dp))

            SettingSection(sectionName = stringResource(R.string.login)) {
                SettingItem(
                    imageVector = Icons.Outlined.ExitToApp,
                    settingName = stringResource(R.string.logout),
                    textColor = Color.InstaBlue,
                    onClickSettingItem = { }
                )
            }
        }
    }
}