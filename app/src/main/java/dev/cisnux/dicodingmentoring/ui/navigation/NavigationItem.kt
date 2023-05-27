package dev.cisnux.dicodingmentoring.ui.navigation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

interface NavigationItem {
    val title: String
    val destination: String
    val contentDescription: String
}

@Immutable
data class ImageVectorNavigationItem(
    val icon: ImageVector,
    override val title: String,
    override val destination: String,
    override val contentDescription: String,
) : NavigationItem

@Immutable
data class PainterNavigationItem(
    val icon: Painter,
    override val title: String,
    override val destination: String,
    override val contentDescription: String,
) : NavigationItem
