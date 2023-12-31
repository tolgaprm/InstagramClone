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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.prmto.core_presentation.components.CircleProfileImage
import com.prmto.core_presentation.navigation.NestedNavigation
import com.prmto.navigation.ProfileNestedScreens

@Composable
fun InstagramBottomNavigation(
    modifier: Modifier = Modifier,
    currentBackStackEntry: NavBackStackEntry?,
    bottomNavigationItems: List<NavigationBottomItem>,
    instaNavigationActions: InstaNavigationActions
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
                    modifier = Modifier.testTag(navigationItem.route),
                    selected = navigationItem.selected,
                    onClick = {
                        instaNavigationActions.navigateToTopLevelDestination(navigationItem.route)
                    },
                    icon = {
                        if (navigationItem.route == NestedNavigation.Profile.route) {
                            CircleProfileImage(
                                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTqHZYyumeGLb9wJKCNqgDtB4q4LYYVTwJYp2cQwcc&s",
                                imageSize = 36.dp,
                                borderWidth = 2.dp
                            )
                        } else {
                            Icon(
                                painter = painterResource(
                                    id = setBottomNavigationIcon(
                                        navigationBottomItem = navigationItem,
                                        currentBackStackEntry = currentBackStackEntry
                                    )
                                ),
                                contentDescription = stringResource(id = navigationItem.contentDescription),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
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
    navigationBottomItem: NavigationBottomItem,
    currentBackStackEntry: NavBackStackEntry?,
): Int {
    return if (navigationBottomItem.selected && currentBackStackEntry?.destination?.route == navigationBottomItem.route) {
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

    if (bottomNavigationItems.any { it.route == currentRoute } &&
        currentRoute != NestedNavigation.Share.route
        || currentRoute == ProfileNestedScreens.Setting.route
        || (currentBackStackEntry?.destination?.parent?.route == NestedNavigation.Profile.route && currentRoute != ProfileNestedScreens.EditProfile.route)
    ) {
        content()
    } else {
        return
    }
}