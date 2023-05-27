package dev.cisnux.dicodingmentoring.ui.navigation

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object AppDestinations {
    const val LOGIN_ROUTE = "login"
    const val REGISTER_ROUTE = "register"
    const val RESET_PASSWORD = "resetpassword"
    const val HOME_ROUTE = "home"
    const val REGISTER_PROFILE_ROUTE = "profile/{id}"
    const val MY_PROFILE_ROUTE = "myprofile"
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
    val navigateToRegisterProfile: (id: String) -> Unit = { id ->
        navController.popBackStack()
        navController.navigate("profile/$id") {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
    val navigateToHome: () -> Unit = {
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
    val takePictureFromGallery: (launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) -> Unit =
        {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            val chooser = Intent.createChooser(intent, "Choose a Picture")
            it.launch(chooser)
        }
}