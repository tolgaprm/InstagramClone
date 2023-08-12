package com.prmto.auth_presentation.register.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prmto.auth_presentation.R
import com.prmto.core_presentation.previews.UiModePreview
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme
import com.prmto.core_presentation.ui.theme.colorBlur

@Composable
fun RegisterScreenBottomSection(
    onNavigateToLogin: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(2.dp)
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.already_have_an_account),
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 16.sp),
                color = colorBlur()
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.login),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .clickable {
                        onNavigateToLogin()
                    }
                    .padding(4.dp)
            )
        }
    }
}

@UiModePreview
@Composable
fun RegisterScreenBottomSectionPreview() {
    InstagramCloneTheme {
        RegisterScreenBottomSection(onNavigateToLogin = {})
    }
}