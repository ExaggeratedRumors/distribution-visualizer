package gui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object Theme {
    val colorPalette = darkColors(
        primary = Color(73, 80, 87),
        surface = Color(74, 81, 88),
        onSurface = Color(	233, 236, 239),
        secondary = Color(	173, 181, 189),
        background = Color(108, 117, 125),
        onPrimary = Color.White,
        onSecondary = Color.White
    )

    val shapes = Shapes(
        small = RoundedCornerShape(8.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(1.dp)
    )

    @Composable
    fun VisualizerTheme(content: @Composable () -> Unit) {
        MaterialTheme (
            colors = colorPalette,
            typography = Typography.mainTypography,
            shapes = shapes,
            content = content
        )
    }
}