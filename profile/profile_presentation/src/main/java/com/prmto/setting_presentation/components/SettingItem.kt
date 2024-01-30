package com.prmto.setting_presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme

@Composable
fun SettingSection(
    modifier: Modifier = Modifier,
    sectionName: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = sectionName,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        content()

    }
}

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    settingName: String,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    onClickSettingItem: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClickSettingItem() }
    ) {
        Icon(
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp),
            imageVector = imageVector,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = settingName,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
            color = textColor
        )
    }
}

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    settingName: String,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
    ) {
        Icon(
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp),
            imageVector = imageVector,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = settingName,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
            color = textColor
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun SettingItemPreview() {
    InstagramCloneTheme {
        SettingItem(
            imageVector = Icons.Default.AccountBox,
            settingName = "Account",
            onClickSettingItem = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun SettingSectionPreview() {
    InstagramCloneTheme {
        SettingSection(
            sectionName = "Account Settings",
            content = {
                SettingItem(
                    imageVector = Icons.Default.Edit,
                    settingName = "Edit Profile",
                    onClickSettingItem = {}
                )
            }
        )
    }
}