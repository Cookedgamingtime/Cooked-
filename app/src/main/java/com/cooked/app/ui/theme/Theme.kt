package com.cooked.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CookedColors = darkColorScheme(
    primary = Color(0xFFFF4D4D),
    secondary = Color(0xFFFFD93D),
    tertiary = Color(0xFF9B5DE5),
    background = Color(0xFF0E0E0E),
    surface = Color(0xFF1A1A1A),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF)
)

@Composable
fun CookedTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = CookedColors, content = content)
}
