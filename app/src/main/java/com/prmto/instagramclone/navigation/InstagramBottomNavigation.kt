package com.prmto.instagramclone.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry

@Composable
fun InstagramBottomNavigation(
    modifier: Modifier = Modifier,
    currentBackStackEntry: NavBackStackEntry?,
    bottomNavigationItems: List<NavigationBottomItem>,
    onNavigate: (NavigationBottomItem) -> Unit
) {
    ShowBottomNavigation(
        currentBackStackEntry = currentBackStackEntry
    ) {
        BottomAppBar(
            modifier = modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.background,
            tonalElevation = 16.dp
        ) {
            bottomNavigationItems.forEach { navigationItem ->
                NavigationBarItem(
                    selected = navigationItem.selected,
                    onClick = {
                        onNavigate(navigationItem)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = setBottomNavigationIcon(navigationItem)),
                            contentDescription = stringResource(id = navigationItem.contentDescription),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.background
                    )
                )
            }
        }
    }
}


private fun setBottomNavigationIcon(
    navigationBottomItem: NavigationBottomItem
): Int {
    return if (navigationBottomItem.selected) {
        navigationBottomItem.selectedIcon
    } else {
        navigationBottomItem.unselectedIcon
    }
}

@Composable
private fun ShowBottomNavigation(
    currentBackStackEntry: NavBackStackEntry?,
    content: @Composable () -> Unit
) {
    val currentRoute = currentBackStackEntry?.destination?.route

    if (bottomNavigationItems.any { it.screen.route == currentRoute }) {
        content()
    } else {
        return
    }
}