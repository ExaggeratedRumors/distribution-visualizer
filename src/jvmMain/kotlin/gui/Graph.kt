package gui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import model.Distribution
import java.lang.Float.max
import java.util.*
import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.pow

data class Point(val x: Float, val y: Float)

class Graph {
    /*
        xMin xMax widthMin widthMax
        yMin yMax heightMax heightMin
     */
    val scale = Array(4) { FloatArray(2) }

    private fun Float.rescale(
        min1: Float,
        max1: Float,
        min2: Float,
        max2: Float
    ) = min2 + (this - min1) * (max2 - min2) / (max1 - min1)

    @Composable
    fun printChart(distribution: Distribution) {
        var tooltipValue by remember { mutableStateOf("x:\ny:") }

        Box(modifier = Modifier
            .size(300.dp, 200.dp)
            .drawBehind {
                val function =
                    distribution.range().map {
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
                val graphPath = Path()
                graphPath.moveTo(function[0].x, function[0].y)
                function.drop(0).forEach { graphPath.lineTo(it.x, it.y) }
                drawPath(graphPath, color = Theme.colorPalette.primary, style = Stroke(width = 3f))

                createGrid(scale[1][0], scale[1][1], scale[3][1], scale[3][0])
                createGrid(scale[0][0], scale[0][1], scale[2][0], scale[2][1])
            }.pointerInput(Unit) {
                awaitPointerEventScope {
                    while(true) {
                        val position = awaitPointerEvent().changes.first().position
                        val x = position.x.rescale(scale[2][0], scale[2][1], scale[0][0], scale[0][1])
                        val y = position.y.rescale(scale[3][0], scale[3][1], scale[1][0], scale[1][1])
                        tooltipValue = "x: %.1f".format(Locale.ROOT, x) + "\ny: %.2f".format(Locale.ROOT, y)
                    }
                }
            }) {
            Text(tooltipValue)
        }
    }

    fun discretization(minY: Float, maxY: Float): List<Float> {
        require(maxY > minY)
        val orderOfMagnitude = 10.0.pow(floor(ln((maxY - minY).toDouble()) * 0.4343))
        return (1..10)
            .map { (it * orderOfMagnitude).toFloat() }
            .filter { it in minY..maxY }
    }

    private fun DrawScope.createGrid(minVal: Float, maxVal: Float, windowStart: Float, windowEnd: Float) {
        val gridPath = Path()
        val gridY = discretization(scale[1][0], scale[1][1])
        gridY.forEach {
            gridPath.moveTo(it, scale[3][1])
            gridPath.lineTo(it, scale[3][0])
        }
        drawPath(gridPath, color = Theme.colorPalette.secondary, style = Stroke(width = 1f))
    }
}