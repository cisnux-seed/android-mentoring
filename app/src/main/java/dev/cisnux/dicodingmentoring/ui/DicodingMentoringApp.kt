package dev.cisnux.dicodingmentoring.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.cisnux.dicodingmentoring.ui.navigation.AppNavGraph

@Composable
fun DicodingMentoringApp(modifier: Modifier = Modifier) {
    val navController: NavHostController = rememberNavController()

    AppNavGraph(navController = navController, modifier = modifier)
}