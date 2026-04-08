package com.example.starstudent.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.example.starstudent.core.domain.Themes


/* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
private val LightColorScheme = lightColorScheme(
    primary = DarkPink,
    onPrimary = White01,
    secondary = Pink,
    onSecondary = White01,
    tertiary = Peach,
    onTertiary = White01,
    background = White01,
    onBackground = DarkPink,
    surface = White01
)

private val lightLabelColorScheme = ExtendedLabelColours(
    red = light_red,
    orange = light_orange,
    yellow = light_yellow,
    green = light_green,
    blue = light_blue,
    navy = light_navy,
    purple = light_purple,
    pink = light_pink,
)

private val DarkColorScheme  = darkColorScheme(
    primary = OffWhite,
    onPrimary = Purple,
    secondary = PalePink,
    onSecondary = Purple,
    tertiary = DarkPink,
    onTertiary = White01,
    background = Purple,
    onBackground = White01,
    surface = White01
)

private val darkLabelColorScheme = ExtendedLabelColours(
    red = dark_red,
    orange = dark_orange,
    yellow = dark_yellow,
    green = dark_green,
    blue = dark_blue,
    navy = dark_navy,
    purple = dark_purple,
    pink = dark_pink,
)

private val StrawberryColorScheme = lightColorScheme(
    primary = strawberry_primary,
    onPrimary = strawberry_onPrimary,
    secondary = strawberry_secondary,
    onSecondary = strawberry_onSecondary,
    tertiary = strawberry_tertiary,
    onTertiary = strawberry_onTertiary,
    background = strawberry_background,
    onBackground = strawberry_onBackground,
    surface = White01
)

private val strawberryLabelColorScheme = ExtendedLabelColours(
    red = strawberry_red,
    orange = strawberry_orange,
    yellow = strawberry_yellow,
    green = strawberry_green,
    blue = strawberry_blue,
    navy = strawberry_navy,
    purple = strawberry_purple,
    pink = strawberry_pink,
)

@Composable
fun StarStudentTheme(
    theme : Themes,
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val colorScheme = when (theme){
        Themes.SYSTEM -> {
            when {
                dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                    if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
                }

                darkTheme -> DarkColorScheme
                else -> LightColorScheme
            }
        }
        Themes.DARK -> DarkColorScheme
        Themes.LIGHT -> LightColorScheme
        Themes.STRAWBERRY -> StrawberryColorScheme
    }

    val extendedColours =
        when(theme){
            Themes.SYSTEM -> if(darkTheme){darkLabelColorScheme}else{lightLabelColorScheme}
            Themes.LIGHT -> lightLabelColorScheme
            Themes.DARK -> darkLabelColorScheme
            Themes.STRAWBERRY -> strawberryLabelColorScheme
        }

    CompositionLocalProvider(
        LocalExtendedLabelColours provides extendedColours
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}