package dev.cisnux.dicodingmentoring.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.cisnux.dicodingmentoring.R
import dev.cisnux.dicodingmentoring.ui.navigation.AppDestinations
import dev.cisnux.dicodingmentoring.ui.navigation.AppNavGraph
import dev.cisnux.dicodingmentoring.ui.navigation.NavigationItem

@Composable
fun DicodingMentoringApp(modifier: Modifier = Modifier) {
    val navController: NavHostController = rememberNavController()

    AppNavGraph(navController = navController, modifier = modifier)
}


@Composable
private fun BottomBar(
    modifier: Modifier = Modifier,
    currentRoute: String?,
    onSelected: (item: NavigationItem) -> Unit,
) {
    NavigationBar(modifier = modifier) {
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                destination = AppDestinations.HOME_ROUTE,
                contentDescription = stringResource(R.string.home_page)
            ),
            NavigationItem(
                title = stringResource(R.string.menu_schedules),
                icon = Icons.Outlined.FavoriteBorder,
                destination = AppDestinations.HOME_ROUTE,
                contentDescription = stringResource(R.string.schedules_page)
            ),
            NavigationItem(
                title = stringResource(R.string.menu_mentorship),
                icon = Icons.Outlined.Person,
                destination = AppDestinations.HOME_ROUTE,
                contentDescription = stringResource(R.string.mentorship_schedules_page)
            )
        )
        navigationItems.forEach { item ->
            NavigationBarItem(
                label = { Text(item.title, modifier = Modifier.clearAndSetSemantics {}) },
                icon = {
                    Icon(item.icon, contentDescription = null)
                },
                selected = currentRoute == item.destination,
                onClick = {
                    onSelected(item)
                },
                modifier = Modifier.semantics(mergeDescendants = true) {
                    contentDescription = item.contentDescription
                }
            )
        }
    }
}