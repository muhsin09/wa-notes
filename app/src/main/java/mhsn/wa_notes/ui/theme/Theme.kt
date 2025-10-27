package mhsn.wa_notes.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = WhatsAppGreen,
    onPrimary = SurfaceLight,
    primaryContainer = WhatsAppGreenLight,
    onPrimaryContainer = WhatsAppGreen,
    secondary = WhatsAppTeal,
    onSecondary = SurfaceLight,
    background = BackgroundLight,
    onBackground = TextPrimary,
    surface = SurfaceLight,
    onSurface = TextPrimary,
    surfaceVariant = BackgroundLight,
    onSurfaceVariant = TextSecondary,
    outline = TextSecondary,
    error = androidx.compose.ui.graphics.Color(0xFFB00020),
    onError = SurfaceLight
)

private val DarkColorScheme = darkColorScheme(
    primary = WhatsAppGreenLight,
    onPrimary = BackgroundDark,
    primaryContainer = WhatsAppTeal,
    onPrimaryContainer = WhatsAppGreenLight,
    secondary = WhatsAppBlue,
    onSecondary = BackgroundDark,
    background = BackgroundDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = BackgroundDark,
    onSurfaceVariant = TextSecondaryDark,
    outline = TextSecondaryDark,
    error = androidx.compose.ui.graphics.Color(0xFFCF6679),
    onError = BackgroundDark
)

@Composable
fun WanotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}