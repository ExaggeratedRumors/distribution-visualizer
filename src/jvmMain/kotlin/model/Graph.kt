package model

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.material.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

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
        Box(modifier = Modifier.size(300.dp, 200.dp)
            .drawBehind {
                val function =
                    (0..100).map {
                        min + it / (max - min)
                    }.map {
                        Point(
                            it.toFloat(),
                            distribution.probabilityDensityFunction(it.toFloat()))
                    }.let {
                        val minHor = it.minOf { v -> v.x }
                        val maxHor = it.maxOf { v -> v.x }
                        val minVer = it.minOf { v -> v.y }
                        val maxVer = it.maxOf { v -> v.y }
                        it.map { p ->
                            val x = p.x.rescale(minHor, maxHor, 0f, size.width)
                            val y = p.y.rescale(minVer, maxVer, size.height, 0f)
                            Point(x, y)
                        }
                    }

                val path = Path()
                path.moveTo(function[0].x, function[0].y)
                function.drop(0).forEach { path.lineTo(it.x, it.y) }
                drawPath(path, color = Color.Cyan, style = Stroke(width = 3f))
            })
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