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

    fun createYGrid(minY: Float, maxY: Float) : List<Float> {
        var diffY = maxY - minY
        var dy = 0f
        var dx = 0f
        val yRange = (1..5)
            .map { minY + (maxY - minY) * it / 5 }
            .let {
                dy = it[1] - it[0]
                var r = 0
                do {
                    dy *= 10f
                    r += 1
                } while(floor(dy) == 0f)
                dy = round(dy)
                dy /= 10f.pow(r)

                it.map { v ->
                    var nv = v
                    var rv = 0
                    do {
                        nv *= 10f
                        rv += 1
                    } while(floor(nv) == 0f)
                    nv = round(nv)
                    nv /= 10f.pow(rv)
                    nv
                }
            }
        return yRange
    }

    fun createGrid(minX: Float, maxX: Float, minY: Float, maxY: Float) =
        Pair(
            createYGrid(minY, maxY),
            createYGrid(minX, maxX)
        )
}