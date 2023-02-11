package gui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.Distribution
import java.util.*
import kotlin.math.*

//class for frame, labels and grid of parameters taken from graph
class PlotPane {
    private val graph: Graph = Graph()

    @Composable
    fun distributionPlot(distribution: Distribution) {
        Column(modifier = Modifier
            .padding(10.dp)
            .border(width = 1.dp, color = Color.Black)
            .padding(5.dp)
            .width(IntrinsicSize.Min)) {
            Row(
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                graph.printChart(distribution)
                printVerticalLabels()
            }
            printHorizontalLabels()
        }
    }

    @Composable
    fun printVerticalLabels() {
        var minY by remember { mutableStateOf( 0f ) }
        var maxY by remember { mutableStateOf( 0f ) }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight().drawBehind {
                minY = graph.scale[1][0]
                maxY = graph.scale[1][1]
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
                minX = graph.scale[0][0]
                maxX = graph.scale[0][1]
            }
        ) {
            Text("%.0f".format(minX))
            Text("%.0f".format(maxX))
        }
    }
}