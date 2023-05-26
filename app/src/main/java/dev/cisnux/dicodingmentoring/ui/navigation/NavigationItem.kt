package dev.cisnux.dicodingmentoring.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val destination: String,
    val contentDescription: String,
)
