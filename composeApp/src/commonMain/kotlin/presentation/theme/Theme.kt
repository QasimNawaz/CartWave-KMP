package presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.russhwolf.settings.Settings
import com.russhwolf.settings.contains
import org.koin.compose.koinInject
import platform.SystemAppearance
import utils.Constants


private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)


private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)

internal val LocalThemeMode = compositionLocalOf { mutableStateOf<ThemeMode>(ThemeMode.System) }

sealed class ThemeMode {
    data object Light : ThemeMode()
    data object Dark : ThemeMode()
    data object System : ThemeMode()
}

@Composable
fun AppTheme(
    systemDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val settings = koinInject<Settings>()

    val isSystem = if (settings.contains(Constants.isAutoDarkMode)) {
        settings.getBoolean(Constants.isAutoDarkMode, true)
    } else {
        settings.putBoolean(Constants.isAutoDarkMode, true)
        true
    }
    val darkTheme = if (settings.contains(Constants.darkTheme)) {
        settings.getBoolean(Constants.darkTheme, false)
    } else {
        settings.putBoolean(Constants.darkTheme, false)
        false
    }
    val themeMode = remember {
        mutableStateOf(
            if (isSystem) {
                ThemeMode.System
            } else {
                if (darkTheme) {
                    ThemeMode.Dark
                } else {
                    ThemeMode.Light
                }
            }
        )
    }
    CompositionLocalProvider(LocalThemeMode provides themeMode) {
        val currentThemeMode by themeMode
        val colors = when (currentThemeMode) {
            ThemeMode.Dark -> DarkColorScheme
            ThemeMode.Light -> LightColorScheme
            ThemeMode.System -> if (systemDarkTheme) DarkColorScheme else LightColorScheme
        }
        val systemAppearanceMode = when (currentThemeMode) {
            ThemeMode.Dark -> true
            ThemeMode.Light -> false
            ThemeMode.System -> systemDarkTheme
        }
        SystemAppearance(!systemAppearanceMode)
        MaterialTheme(
            colorScheme = colors, content = content
        )
    }
}