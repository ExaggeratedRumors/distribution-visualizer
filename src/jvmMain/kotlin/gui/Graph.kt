package model

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import gui.Theme

data class Point(val x: Float, val y: Float)

class Graph {
    private fun Float.rescale(
        min1: Float,
        max1: Float,
        min2: Float,
        max2: Float
    ) = min2 + (this - min1) * (max2 - min2) / (max1 - min1)

    @Composable
    fun printChart(distribution: Distribution, min: Float, max: Float) {
        var tooltipValue by remember { mutableStateOf("(0,0)") }
        val scale = Array(4) { FloatArray(2) }

        Box(modifier = Modifier
            .size(300.dp, 200.dp)
            .drawBehind {
                val function =
                    (0..100).map {
                        min + it / (max - min)
                    }.map {
                        Point(
                            it,
                            distribution.probabilityDensityFunction(it))
                    }.let {
                        scale[0][0] = it.minOf { v -> v.x }
                        scale[0][1] = it.maxOf { v -> v.x }
                        scale[1][0] = it.minOf { v -> v.y }
                        scale[1][1] = it.maxOf { v -> v.y }
                        scale[2][0] = 0f
                        scale[2][1] = size.width
                        scale[3][0] = size.height
                        scale[3][1] = 0f
                        it.map { p ->
                            val x = p.x.rescale(scale[0][0], scale[0][1], scale[2][0], scale[2][1])
                            val y = p.y.rescale(scale[1][0], scale[1][1], scale[3][0], scale[3][1])
                            Point(x, y)
                        }
                    }
                val path = Path()
                path.moveTo(function[0].x, function[0].y)
                function.drop(0).forEach { path.lineTo(it.x, it.y) }
                drawPath(path, color = Theme.colorPalette.primary, style = Stroke(width = 3f))
            }.pointerInput(Unit) {
                awaitPointerEventScope {
                    while(true) {
                        val position = awaitPointerEvent().changes.first().position
                        val x = position.x.rescale(scale[0][0], scale[0][1], scale[2][0], scale[2][1])
                        tooltipValue = "(${position.x},${position.y})"
                    }
                }
            }) {
            Text(tooltipValue)
        }
    }

    @Composable
    fun printVerticalLabels() {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "1")
            Text(text = "0")
        }
    }

    @Composable
    fun printHorizontalLabels() {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("-3")
            Text("3")
        }
    }

    @Composable
    fun createDistributionChart(distribution: Distribution) {
        Column(
            Modifier
                .padding(10.dp)
                .border(width = 1.dp, color = Color.Black)
                .padding(5.dp)
                .width(IntrinsicSize.Min)
        ) {
            Row(Modifier.height(IntrinsicSize.Min)) {
                printVerticalLabels()
                printChart(distribution, 0f, 1f)
            }
            printHorizontalLabels()
        }
    }
}