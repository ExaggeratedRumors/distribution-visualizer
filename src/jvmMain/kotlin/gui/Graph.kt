package gui

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
import model.Distribution
import java.lang.Float.max
import java.util.*

data class Point(val x: Float, val y: Float)

class Graph {
    private val scale = Array(4) { FloatArray(2) }

    private fun getMinX() = scale[0][0]
    private fun getMaxX() = scale[0][1]
    private fun getMinY() = scale[1][0]
    private fun getMaxY() = scale[1][1]

    private fun Float.rescale(
        min1: Float,
        max1: Float,
        min2: Float,
        max2: Float
    ) = min2 + (this - min1) * (max2 - min2) / (max1 - min1)

    @Composable
    fun printChart(distribution: Distribution, min: Float, max: Float) {
        var tooltipValue by remember { mutableStateOf("x:\ny:") }

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
                        val x = max(0f, position.x.rescale(scale[2][0], scale[2][1], scale[0][0], scale[0][1]))
                        val y = max(0f, position.y.rescale(scale[3][0], scale[3][1], scale[1][0], scale[1][1]))
                        tooltipValue = "x: %.0f".format(x) + "\ny: %.2f".format(Locale.ROOT, y)
                    }
                }
            }) {
            Text(tooltipValue)
        }
    }

    @Composable
    fun printVerticalLabels() {
        var minY by remember { mutableStateOf( 0f ) }
        var maxY by remember { mutableStateOf( 0f ) }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight().drawBehind {
                minY = getMinY()
                maxY = getMaxY()
            }
        ) {
            Text("%.2f".format(Locale.ROOT, maxY))
            Text("%.0f".format(minY))
        }
    }

    @Composable
    fun printHorizontalLabels() {
        var minX by remember { mutableStateOf( 0f ) }
        var maxX by remember { mutableStateOf( 0f ) }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().drawBehind {
                minX = getMinX()
                maxX = getMaxX()
            }
        ) {
            Text("%.0f".format(minX))
            Text("%.0f".format(maxX))
        }
    }

    @Composable
    fun createDistributionChart(distribution: Distribution) {
        Column(modifier = Modifier
                .padding(10.dp)
                .border(width = 1.dp, color = Color.Black)
                .padding(5.dp)
                .width(IntrinsicSize.Min)) {
            Row(
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                printChart(distribution, 0f, 1f)
                printVerticalLabels()
            }
            printHorizontalLabels()
        }
    }
}