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
        primary = Color(79, 67, 103),
        surface = Color(77, 65, 102),
        onSurface = Color(199, 211, 221),
        secondary = Color(27, 26, 36),
        background = Color(40, 40, 57),
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