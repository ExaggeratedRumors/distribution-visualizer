package gui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object Theme {
    public val colorPalette = darkColors(
        primary = Color(112, 54, 111),
        surface = Color(110, 52, 109),
        onSurface = Color(199, 211, 221),
        secondary = Color(53, 64, 118),
        background = Color(110, 52, 109)
    )

    public val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
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