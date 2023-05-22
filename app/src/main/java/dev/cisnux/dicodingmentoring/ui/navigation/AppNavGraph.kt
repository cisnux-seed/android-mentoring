package dev.cisnux.dicodingmentoring.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.cisnux.dicodingmentoring.ui.home.HomeScreen
import dev.cisnux.dicodingmentoring.ui.login.LoginScreen
import dev.cisnux.dicodingmentoring.ui.register.RegisterScreen
import dev.cisnux.dicodingmentoring.ui.registerprofile.RegisterProfileScreen
import dev.cisnux.dicodingmentoring.ui.resetpassword.ResetPasswordScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier,
    startDestination: String = AppDestinations.HOME_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        val navigationActions =
            AppNavigationActions(navController)
        composable(
            route = AppDestinations.LOGIN_ROUTE,
        ) {
            LoginScreen(
                navigateToHome = navigationActions.navigateToHome,
                navigateToRegister = navigationActions.navigateToRegister,
                navigateToResetPassword = navigationActions.navigateToResetPassword
            )
        }
        composable(
            route = AppDestinations.REGISTER_ROUTE,
        ) {
            RegisterScreen(
                navigateToHome = navigationActions.navigateToRegisterProfile,
                navigateToLogin = navigationActions.navigateUp
            )
        }
        composable(
            route = AppDestinations.RESET_PASSWORD,
        ) {
            ResetPasswordScreen(
                navigateToSignIn = navigationActions.navigateUp
            )
        }
        composable(
            route = AppDestinations.HOME_ROUTE
        ) {
            HomeScreen(
                navigateToLogin = navigationActions.navigateToLogin,
                navigateToRegisterProfile = navigationActions.navigateToRegisterProfile
            )
        }
        composable(
            route = AppDestinations.PROFILE_ROUTE,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {
            val id = it.arguments?.getString("id")
            id?.let { uid ->
                RegisterProfileScreen(
                    id = uid,
                    onNavigateToHome = navigationActions.navigateToHome,
                    takePictureFromGallery = navigationActions.takePictureFromGallery
                )
            }
        }
    }
}