package com.prmto.home_presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prmto.core_presentation.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeTopBar(
    onNavigateToMessageScreen: () -> Unit
) {
    TopAppBar(
        title = {
            Icon(
                painter = painterResource(id = R.drawable.logo),
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = stringResource(R.string.logo),
                modifier = Modifier
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                spotColor = MaterialTheme.colorScheme.onBackground,
                ambientColor = MaterialTheme.colorScheme.onBackground
            ),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        actions = {
            Icon(
                painter = painterResource(id = R.drawable.un_like),
                contentDescription = stringResource(R.string.notifications)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                painter = painterResource(id = R.drawable.messages_unselected),
                contentDescription = stringResource(R.string.notifications),
                modifier = Modifier.clickable {
                    onNavigateToMessageScreen()
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

        }
    )
}