package gui

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font

object Utils {
    var appName = "Distribution visualizer"
    var iconPath= "icon.png"
    val font = FontFamily(
        Font(
            resource = "linbiolinum_sansserif-bold.ttf",
            weight = FontWeight.W400,
            style = FontStyle.Normal
        )
    )

}