package gui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import model.Distribution
import java.util.*
import kotlin.math.*

class PlotPane {
    data class Point(val x: Float, val y: Float)
    companion object Scale {
        var xMin = 0f
        var xMax = 0f
        var yMin = 0f
        var yMax = 0f
        var widthMin = 0f
        var widthMax = 0f
        var heightMin = 0f
        var heightMax = 0f
    }

    @Composable
    fun printPlot(distribution: Distribution) {
        Column(modifier = Modifier
            .padding(10.dp)
            .border(width = 1.dp, color = Color.Black)
            .padding(5.dp)
            .width(IntrinsicSize.Min)
        ) {
            var tooltipValue by remember { mutableStateOf("x:\ny:") }
            Row(
                modifier = Modifier
                    .size(300.dp,200.dp)
                    .height(IntrinsicSize.Min)
                    .drawBehind {
                        printGraph(distribution)
                        printGrid(xMin, xMax, widthMin, widthMax)
                        printGrid(yMin, yMax, heightMax, heightMin)
                    }.pointerInput(Unit) {
                        printTooltip { tooltipValue = it }
                    }
            ) {
                printVerticalLabels()
                Text(tooltipValue)
            }
            printHorizontalLabels()
        }
    }

    private fun Float.rescale(
        min1: Float,
        max1: Float,
        min2: Float,
        max2: Float
    ) = min2 + (this - min1) * (max2 - min2) / (max1 - min1)

    private fun DrawScope.printGraph(distribution: Distribution) {
        val function = distribution.range().map {
            Point(
                it,
                distribution.probabilityDensityFunction(it))
        }.let {
            xMin = it.minOf { v -> v.x }
            xMax = it.maxOf { v -> v.x }
            yMin = it.minOf { v -> v.y }
            yMax = it.maxOf { v -> v.y }
            widthMin = 0f
            widthMax = size.width
            heightMax = size.height
            heightMin = 0f
            it.map { p ->
                val x = p.x.rescale(xMin, xMax, widthMin, widthMax)
                val y = p.y.rescale(yMin, yMax, heightMax, heightMin)
                Point(x, y)
            }
        }
        val graphPath = Path()
        graphPath.moveTo(function[0].x, function[0].y)
        function.drop(0).forEach { graphPath.lineTo(it.x, it.y) }
        drawPath(graphPath, color = Theme.colorPalette.primary, style = Stroke(width = 3f))
    }

    private suspend fun PointerInputScope.printTooltip(
        updateTooltip: (tooltipValue: String) -> Unit
    ) {
        awaitPointerEventScope {
            while(true) {
                val position = awaitPointerEvent().changes.first().position
                val x = position.x.rescale(widthMin, widthMax, xMin, xMax)
                val y = position.y.rescale(heightMax, heightMin, yMin, yMax)
                updateTooltip("x: %.1f".format(Locale.ROOT, x) + "\ny: %.2f".format(Locale.ROOT, y))
            }
        }
    }

    private fun DrawScope.printGrid(minVal: Float, maxVal: Float, windowStart: Float, windowEnd: Float) {
        val gridPath = Path()
        val orderOfMagnitude = 10.0.pow(floor(ln((maxVal - minVal).toDouble()) * 0.4343))
        (1..10)
            .map { (it * orderOfMagnitude).toFloat() }
            .filter { it in minVal..maxVal }
            .forEach {
                gridPath.moveTo(it, windowStart)
                gridPath.lineTo(it, windowEnd)
            }
        drawPath(gridPath, color = Theme.colorPalette.secondary, style = Stroke(width = 1f))
    }

    @Composable
    fun printVerticalLabels() {
        var minY by remember { mutableStateOf( 0f ) }
        var maxY by remember { mutableStateOf( 0f ) }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight().drawBehind {
                minY = yMin
                maxY = yMax
            }
        ) {
            Text("%.2f".format(Locale.ROOT, maxY))
            Text("%.0f".format(minY))
        }
    }

    @Composable
    fun printHorizontalLabels() {
        var minX by remember { mutableStateOf(0f) }
        var maxX by remember { mutableStateOf(0f) }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().drawBehind {
                minX = xMin
                maxX = xMax
            }
        ) {
            Text("%.0f".format(minX))
            Text("%.0f".format(maxX))
        }
    }
}