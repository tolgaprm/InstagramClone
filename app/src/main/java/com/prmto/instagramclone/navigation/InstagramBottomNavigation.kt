package com.prmto.instagramclone.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.prmto.core_presentation.navigation.Screen

@Composable
fun InstagramBottomNavigation(
    modifier: Modifier = Modifier,
    currentBackStackEntry: NavBackStackEntry?,
    bottomNavigationItems: List<NavigationBottomItem>,
    onNavigate: (NavigationBottomItem) -> Unit
) {
    ShowBottomNavigation(
        currentBackStackEntry = currentBackStackEntry,
    ) {
        BottomAppBar(
            modifier = modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 8.dp,
                    spotColor = MaterialTheme.colorScheme.onBackground,
                    ambientColor = MaterialTheme.colorScheme.onBackground
                )
                .height(50.dp)
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
                        indicatorColor = MaterialTheme.colorScheme.background,
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


    if (bottomNavigationItems.any { it.screen.route == currentRoute } && currentRoute != Screen.Share.route) {
        content()
    } else {
        return
    }
}