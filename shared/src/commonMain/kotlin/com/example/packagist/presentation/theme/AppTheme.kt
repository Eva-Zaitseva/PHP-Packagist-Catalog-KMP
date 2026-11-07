package com.example.packagist.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF5146A5),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFE8E3FF),
    onPrimaryContainer = Color(0xFF1B1460),
    secondary = Color(0xFF625B71),
    onSecondary = Color(0xFFFFFFFF),
    background = Color(0xFFFAF9FF),
    onBackground = Color(0xFF1B1B21),
    surface = Color(0xFFFAF9FF),
    onSurface = Color(0xFF1B1B21),
    surfaceVariant = Color(0xFFE8E5F0),
    onSurfaceVariant = Color(0xFF48454F),
    outline = Color(0xFF797680)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFC7BFFF),
    onPrimary = Color(0xFF2B236F),
    primaryContainer = Color(0xFF40378A),
    onPrimaryContainer = Color(0xFFE8E3FF),
    secondary = Color(0xFFCBC3D9),
    onSecondary = Color(0xFF332D40),
    background = Color(0xFF131318),
    onBackground = Color(0xFFE5E1E9),
    surface = Color(0xFF131318),
    onSurface = Color(0xFFE5E1E9),
    surfaceVariant = Color(0xFF48454F),
    onSurfaceVariant = Color(0xFFCAC5D0),
    outline = Color(0xFF938F9A)
)

@Composable
fun AppTheme(
    darkTheme: Boolean? = null,
    content: @Composable () -> Unit
) {
    val useDarkTheme = darkTheme ?: isSystemInDarkTheme()

    MaterialTheme(
        colorScheme = if (useDarkTheme) DarkColors else LightColors,
        content = content
    )
}