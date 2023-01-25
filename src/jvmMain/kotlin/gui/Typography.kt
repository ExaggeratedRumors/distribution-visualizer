package gui

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

object Typography {
    private val mainFont = Font(
        resource = "linbiolinum_sansserif-bold.ttf",
        weight = FontWeight.W400,
        style = FontStyle.Normal
    )

    val mainTypography = Typography(
        body1 = TextStyle(
            fontFamily = FontFamily(mainFont),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    )
}