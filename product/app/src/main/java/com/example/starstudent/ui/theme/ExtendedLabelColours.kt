package com.example.starstudent.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ExtendedLabelColours(
    val red : Color,
    val orange : Color,
    val yellow : Color,
    val green : Color,
    val blue : Color,
    val navy : Color,
    val purple: Color,
    val pink : Color
)

val LocalExtendedLabelColours = staticCompositionLocalOf {
    ExtendedLabelColours(
        red = Color.Unspecified,
        orange = Color.Unspecified,
        yellow = Color.Unspecified,
        green = Color.Unspecified,
        blue = Color.Unspecified,
        navy = Color.Unspecified,
        purple = Color.Unspecified,
        pink = Color.Unspecified
    )
}
