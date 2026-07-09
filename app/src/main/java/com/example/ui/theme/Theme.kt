package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = PurplePrimary,
    secondary = BlueSecondary,
    tertiary = CyanAccent,
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    onPrimary = WhiteText,
    onSecondary = WhiteText,
    onTertiary = DarkBackground,
    onBackground = WhiteText,
    onSurface = WhiteText,
    onSurfaceVariant = GrayText
  )

private val LightColorScheme = DarkColorScheme // Force dark theme for premium look

@Composable
fun PowerVPNTheme(
  darkTheme: Boolean = true, // Always dark
  dynamicColor: Boolean = false, // Disable dynamic color for custom theme
  content: @Composable () -> Unit,
) {
  val colorScheme = DarkColorScheme

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
