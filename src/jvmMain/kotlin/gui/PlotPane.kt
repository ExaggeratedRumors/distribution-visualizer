package gui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.Distribution
import kotlin.math.*

//class for frame, labels and grid of parameters taken from graph
class PlotPane {
    @Composable
    fun distributionPlot(distribution: Distribution) {
        val graph = Graph()
        Column(modifier = Modifier
            .padding(10.dp)
            .border(width = 1.dp, color = Color.Black)
            .padding(5.dp)
            .width(IntrinsicSize.Min)) {
            Row(
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                graph.printChart(distribution)
                graph.printVerticalLabels()
            }
            graph.printHorizontalLabels()
        }
    }

    fun discretization(minY: Float, maxY: Float): List<Float> {
        require(maxY > minY)
        val orderOfMagnitude = 10.0.pow(floor(ln((maxY - minY).toDouble()) * 0.4343))
        return (1..10)
            .map { (it * orderOfMagnitude).toFloat() }
            .filter { it in minY..maxY }
    }

    fun createGrid(minX: Float, maxX: Float, minY: Float, maxY: Float) =
        Pair(
            discretization(minY, maxY),
            discretization(minX, maxX)
        )
}