package gui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.Distribution

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
}