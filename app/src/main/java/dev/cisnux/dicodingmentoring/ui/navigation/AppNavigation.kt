package dev.cisnux.dicodingmentoring.ui.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object AppDestinations {
    const val LOGIN_ROUTE = "login"
    const val REGISTER_ROUTE = "register"
    const val RESET_PASSWORD = "resetpassword"
    const val HOME_ROUTE = "home/{id}"
}

class AppNavigationActions(
    navController: NavHostController,
) {
    val navigateToLogin: () -> Unit = {
        navController.popBackStack()
        navController.navigate(AppDestinations.LOGIN_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
    val navigateToRegister: () -> Unit = {
        navController.navigate(AppDestinations.REGISTER_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
    val navigateToResetPassword: () -> Unit = {
        navController.navigate(AppDestinations.RESET_PASSWORD) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
    val navigateToHome: (String) -> Unit = { _ ->
        navController.popBackStack()
        navController.navigate(AppDestinations.HOME_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}