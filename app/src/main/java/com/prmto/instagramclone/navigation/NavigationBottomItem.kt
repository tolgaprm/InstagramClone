package com.prmto.instagramclone.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.prmto.core_presentation.navigation.NestedNavigation
import com.prmto.core_presentation.navigation.Screen
import com.prmto.instagramclone.R
import com.prmto.core_presentation.R as coreRes

data class NavigationBottomItem(
    val selected: Boolean = false,
    val route: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    @StringRes val contentDescription: Int
)

val bottomNavigationItems = listOf(
    NavigationBottomItem(
        selected = true,
        route = Screen.Home.route,
        selectedIcon = R.drawable.home_selected,
        unselectedIcon = R.drawable.home_unselected,
        contentDescription = R.string.home
    ),
    NavigationBottomItem(
        route = Screen.Search.route,
        selectedIcon = R.drawable.search_selected,
        unselectedIcon = R.drawable.search_unselected,
        contentDescription = R.string.search
    ),
    NavigationBottomItem(
        route = Screen.Share.route,
        selectedIcon = R.drawable.new_post_selected,
        unselectedIcon = R.drawable.new_post_unselected,
        contentDescription = R.string.add_post
    ),
    NavigationBottomItem(
        route = Screen.Reels.route,
        selectedIcon = R.drawable.reels_selected,
        unselectedIcon = R.drawable.reels_unselected,
        contentDescription = R.string.reels
    ),
    NavigationBottomItem(
        route = NestedNavigation.Profile.route,
        selectedIcon = coreRes.drawable.account,
        unselectedIcon = coreRes.drawable.account,
        contentDescription = R.string.profile
    )
)

@Composable
fun rememberBottomNavigationItems() = rememberSaveable {
    mutableStateOf(bottomNavigationItems)
}
