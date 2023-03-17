package gui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
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
    fun printPlotPane(distribution: Distribution) {
        Column(modifier = Modifier
            .padding(top = 20.dp, bottom = 0.dp),
        ) {
            Row {
                printVerticalLabels()
                printChartScape(distribution)
                Column (modifier = Modifier
                    .padding(3.dp)
                    .width(IntrinsicSize.Min)
                    .size(28.dp,200.dp)
                ){ }
            }
        }
        printHorizontalLabels()
    }

    private fun Float.rescale(
        min1: Float,
        max1: Float,
        min2: Float,
        max2: Float
    ) = min2 + (this - min1) * (max2 - min2) / (max1 - min1)

    private fun DrawScope.scaleDistribution(distribution: Distribution) = distribution
        .range()
        .map {
            Point(it, distribution.probabilityDensityFunction(it))
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

    private fun DrawScope.printGraph(function: List<Point>) {
        val graphPath = Path()
        graphPath.moveTo(function[0].x, function[0].y)
        function.drop(0).forEach { graphPath.lineTo(it.x, it.y) }
        drawPath(graphPath, color = Theme.colorPalette.primary, style = Stroke(width = 3f))
    }

    private fun DrawScope.printGrid() {
        val gridPath = Path()
        val orderOfMagnitudeX = 10.0.pow(floor(ln((xMax - xMin - 1).toDouble()) * 0.4343))
        (-5..5)
            .map { (it * orderOfMagnitudeX).toFloat() }
            .filter { it in xMin..xMax }
            .map { it.rescale(xMin, xMax, widthMin, widthMax) }
            .forEach {
                gridPath.moveTo(it, heightMax)
                gridPath.lineTo(it, heightMin)
            }
        drawPath(gridPath, color = Theme.colorPalette.onBackground, style = Stroke(width = 1f))

        gridPath.reset()
        val orderOfMagnitudeY = 10.0.pow(floor(ln((yMax - yMin - 1).toDouble()) * 0.4343))
        (0..10)
            .map { (it * orderOfMagnitudeY).toFloat() }
            .filter { it in yMin..yMax }
            .map { it.rescale(yMin, yMax, heightMax, heightMin) }
            .forEach {
                gridPath.moveTo(widthMin, it)
                gridPath.lineTo(widthMax, it)
            }
        drawPath(gridPath, color = Theme.colorPalette.onBackground, style = Stroke(width = 1f))
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

    @Composable
    fun printChartScape(distribution: Distribution) {
        var tooltipValue by remember { mutableStateOf("x:\ny:") }
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .size(250.dp,200.dp)
                .height(IntrinsicSize.Min)
                .border(width = 1.dp, color = Color.Black)
                .padding(3.dp)
                .drawBehind {
                    val scaledDistribution = scaleDistribution(distribution)
                    printGrid()
                    printGraph(scaledDistribution)
                }.pointerInput(Unit) {
                    printTooltip { tooltipValue = it }
                }
        ) {
            Text(tooltipValue)
        }
    }
    @Composable
    fun printVerticalLabels() {
        var minY by remember { mutableStateOf( 0f ) }
        var maxY by remember { mutableStateOf( 0f ) }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .padding(3.dp)
                .width(IntrinsicSize.Min)
                .size(28.dp,200.dp)
                .drawBehind {
                    minY = yMin
                    maxY = yMax
                }
        ) {
            Text("%.2f".format(Locale.ROOT, maxY))
            Text("%.2f".format(Locale.ROOT,minY))
        }
    }

    @Composable
    fun printHorizontalLabels() {
        var minX by remember { mutableStateOf(0f) }
        var maxX by remember { mutableStateOf(0f) }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .size(280.dp,30.dp)
                .drawBehind {
                minX = xMin
                maxX = xMax
            }
        ) {
            Text("%.2f".format(Locale.ROOT,minX))
            Text("%.2f".format(Locale.ROOT,maxX))
        }
    }
}