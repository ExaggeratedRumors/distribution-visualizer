package gui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.Distribution
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.round

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

    fun createGrid(minX: Float, maxX: Float, minY: Float, maxY: Float) {
        var diffX = maxX - minX
        var diffY = maxY - minY
        var dy = 0f
        var dx = 0f
        val yRange = (1..5)
            .map { minY + diffY * it / 5 }
            .let {
                dy = it[0]
                while(floor(dy) == 0f) {
                    dy *= 10f
                }
            }
    }
}