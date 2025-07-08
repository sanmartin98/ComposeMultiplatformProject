package org.example.project

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val lightColorScheme = lightColorScheme(
    primary = Color.Red
)

private val darkColorScheme = darkColorScheme(
    primary = Color.Magenta
)

@Composable
fun KmpTheme(content: @Composable () -> Unit) {
    val isDarkTheme = isSystemInDarkTheme()
    val colorScheme = if (isDarkTheme) darkColorScheme else lightColorScheme

    MaterialTheme(
        content = content,
        colorScheme = colorScheme
    )
}