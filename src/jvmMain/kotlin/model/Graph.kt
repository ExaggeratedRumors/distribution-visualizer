package model

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*

class Graph (
    val modifier: Modifier,
    val xAxis: List<Int>,
    val yAxis: List<Int>,
    val data: List<Float>,
    val padding: Dp
    ){

    @Composable
    fun paintGraph() {
        Box(
            modifier = this.modifier
                .background(Color.White)
                .padding(horizontal = 8.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas (modifier = Modifier.fillMaxSize()) {

            }
        }
    }

}