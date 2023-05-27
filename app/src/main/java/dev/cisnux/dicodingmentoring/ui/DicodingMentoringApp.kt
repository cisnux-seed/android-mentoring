package dev.cisnux.dicodingmentoring.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.cisnux.dicodingmentoring.R
import dev.cisnux.dicodingmentoring.ui.navigation.AppDestinations
import dev.cisnux.dicodingmentoring.ui.navigation.AppNavGraph
import dev.cisnux.dicodingmentoring.ui.navigation.ImageVectorNavigationItem
import dev.cisnux.dicodingmentoring.ui.navigation.PainterNavigationItem
import dev.cisnux.dicodingmentoring.ui.theme.DicodingMentoringTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DicodingMentoringApp(modifier: Modifier = Modifier) {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute == AppDestinations.HOME_ROUTE
                && currentRoute == AppDestinations.MY_PROFILE_ROUTE
            ) {
                BottomBar(currentRoute = currentRoute, onSelected = { destination ->
                    navController.navigate(destination) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                })
            }
        }
    ) { innerPadding ->
        AppNavGraph(navController = navController, modifier = modifier.padding(innerPadding))
    }
}

@Preview
@Composable
fun BottomBarPreview(modifier: Modifier = Modifier) {
    DicodingMentoringTheme {
        BottomBar(currentRoute = "", onSelected = {})
    }
}

@Composable
private fun BottomBar(
    modifier: Modifier = Modifier,
    currentRoute: String?,
    onSelected: (destination: String) -> Unit,
) {
    NavigationBar(modifier = modifier) {
        val navigationItems = listOf(
            ImageVectorNavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                destination = AppDestinations.HOME_ROUTE,
                contentDescription = stringResource(R.string.home_page)
            ),
            PainterNavigationItem(
                title = stringResource(R.string.menu_mentorship),
                icon = painterResource(id = R.drawable.ic_event_available_24),
                destination = AppDestinations.HOME_ROUTE,
                contentDescription = stringResource(R.string.mentorship_page)
            ),
            PainterNavigationItem(
                title = stringResource(R.string.chat_menu),
                icon = painterResource(id = R.drawable.ic_chat_24),
                destination = AppDestinations.HOME_ROUTE,
                contentDescription = stringResource(R.string.chat_page)
            ),
            ImageVectorNavigationItem(
                title = stringResource(R.string.menu_my_profile),
                icon = Icons.Filled.AccountCircle,
                destination = AppDestinations.MY_PROFILE_ROUTE,
                contentDescription = stringResource(R.string.my_profile_page)
            )
        )
        navigationItems.forEach { item ->
            NavigationBarItem(
                label = { Text(item.title, modifier = Modifier.clearAndSetSemantics {}) },
                icon = {
                    if (item is PainterNavigationItem) {
                        Icon(
                            painter = item.icon,
                            contentDescription = null
                        )
                    } else if (item is ImageVectorNavigationItem) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null
                        )
                    }
                },
                selected = currentRoute == item.destination,
                onClick = {
                    onSelected(item.destination)
                },
                modifier = Modifier.semantics(mergeDescendants = true) {
                    contentDescription = item.contentDescription
                }
            )
        }
    }
}