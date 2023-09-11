package com.prmto.edit_profile_presentation.navigation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prmto.core_presentation.ui.theme.InstaBlue
import com.prmto.profile_presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileTopBar(
    modifier: Modifier = Modifier,
    isShowSaveButton: Boolean,
    onClickClose: () -> Unit,
    onClickSave: () -> Unit
) {
    TopAppBar(
        modifier = modifier.shadow(elevation = 8.dp),
        title = {
            Text(
                text = stringResource(id = R.string.edit_profile)
            )
        },
        navigationIcon = {
            IconButton(onClick = onClickClose) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close)
                )
            }
        },
        actions = {
            AnimatedVisibility(visible = isShowSaveButton) {
                IconButton(onClick = onClickSave) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(R.string.saved),
                        tint = Color.InstaBlue
                    )
                }
            }
        }
    )
}