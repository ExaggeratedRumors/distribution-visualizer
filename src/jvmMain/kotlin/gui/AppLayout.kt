package gui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.Distribution
import model.Gauss
import model.Graph
import model.Linear

class AppLayout {
    private lateinit var chosenDistribution : MutableState<String>
    private lateinit var showGrid : MutableState<Boolean>

    @Composable
    fun twoColumnsLayout() {
        chosenDistribution = remember { mutableStateOf("None") }
        showGrid = remember { mutableStateOf(true) }
        Row (Modifier.fillMaxSize()) {
            leftPaneContent()
            Divider (
                color = Theme.colorPalette.primary,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            rightPaneContent()
        }
    }

    @Composable
    fun leftPaneContent() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.7f)
                .padding(20.dp)
        ) {
            Row {
                Text (text = "Chosen distribution: ${chosenDistribution.value}", modifier = Modifier.weight(1f))
                Text (text = "Left Pane Second Text Box", modifier = Modifier.weight(1f))
            }

            Spacer (Modifier.size(20.dp))
            Column (
                Modifier.weight(1f)
                    .border(1.dp, color = Theme.colorPalette.primary)
                    .fillMaxWidth(0.7f)
                    .background(color = Theme.colorPalette.onSurface)
            ) {
                val graph = Graph()
                val testDis = Gauss(50f, 10f)
                graph.createDistributionChart(testDis)
            }
            Spacer (Modifier.size(20.dp))
            Row (verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    showGrid.value,
                    { showGrid.value = it },
                    enabled = true,
                    colors = CheckboxDefaults.colors(Theme.colorPalette.primary)
                )
                Text("Show Grid")
            }
        }
    }

    @Composable
    fun rightPaneContent() {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text (text = "Input mean")
            Spacer (Modifier.size(20.dp))
            Row {
                Text (text = "Input SD", modifier = Modifier.weight(1f))
                Spacer (Modifier.size(20.dp))
                Text (text = "[ ]", modifier = Modifier.weight(1f))
            }
            ExtendedFloatingActionButton(
                onClick = { chosenDistribution.value = if (chosenDistribution.value == "None") "Gaussian" else "None" },
                text = { Text(text = "Change distribution") }

            )
        }
    }
}