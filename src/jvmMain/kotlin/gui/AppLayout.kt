package gui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import model.*
import model.Distribution
import model.Gauss
import java.util.*

class AppLayout {
    private var distributions = mapOf(
        "Continuous" to Continuous(),
        "Binomial" to Binomial(),
        "Geometric" to Geometric(),
        "Exponential" to Exponential(),
        "Poisson" to Poisson(),
        "Pareto" to Pareto(),
        "Gauss" to Gauss(),
        "LogNormal" to LogNormal(),
        "Gumbel" to Gumbel()
    )

    private lateinit var chosenDistribution : MutableState<Distribution>
    private lateinit var showGrid : MutableState<Boolean>
    @Composable
    fun twoColumnsLayout() {
        chosenDistribution = remember { mutableStateOf(Gauss(0f, 1f))}
        showGrid = remember { mutableStateOf(true) }
        Row (modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorPalette.background)
        ) {
            leftPaneContent()
            Divider (
                color = Theme.colorPalette.background,
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
                .padding(20.dp)
                .fillMaxHeight()
                .fillMaxWidth(0.7f)
                .clip(Theme.shapes.small)
                .background(Theme.colorPalette.secondary)
        ) {
            Spacer (Modifier.size(20.dp))
            Column (
                Modifier.weight(1f)
                    .border(1.dp, color = Theme.colorPalette.primary)
                    .fillMaxWidth(0.7f)
                    .background(color = Theme.colorPalette.onSurface),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val plotPane = PlotPane()
                plotPane.printPlotPane(chosenDistribution.value, showGrid.value)
            }
            Spacer (Modifier.size(20.dp))
            Row (verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    showGrid.value,
                    { showGrid.value = it },
                    enabled = true,
                    colors = CheckboxDefaults.colors(Theme.colorPalette.primary)
                )
                Text(
                    text = "Show Grid",
                    color = Theme.colorPalette.onSecondary
                )
            }
        }
    }

    @Composable
    fun rightPaneContent() {
        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
                .clip(Theme.shapes.small)
                .background(Theme.colorPalette.secondary)
        ) {
            Spacer (Modifier.size(20.dp))
            Text (
                text = "Mean: %.2f".format(Locale.ROOT, chosenDistribution.value.mean()),
                color = Theme.colorPalette.onSecondary
            )
            Text (
                text = "SD: %.2f".format(Locale.ROOT, chosenDistribution.value.standardDeviation()),
                color = Theme.colorPalette.onSecondary
            )
            Spacer (Modifier.size(20.dp))
            switchDistribution()
            Spacer (Modifier.size(20.dp))
            /*ExtendedFloatingActionButton(
                onClick = { chosenDistribution.value = if (chosenDistribution.value is Gauss) Poisson(1f) else Gauss(0f, 1f) },
            text = { Text(text = "Change distribution") }
            )*/
        }
    }

    @Composable
    fun switchDistribution() {
        var expanded by remember { mutableStateOf(false) }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(150.dp, 50.dp)
                .clip(Theme.shapes.small)
                .background(Theme.colorPalette.primary)
                .clickable(onClick = { expanded = true })
        ) {
            Text(
                text = chosenDistribution.value::class.simpleName!!,
                color = Theme.colorPalette.onSecondary,
                //textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .size(150.dp, (50 * distributions.size).dp)
                    .background(Theme.colorPalette.primary)
                    .clip(Theme.shapes.small)
            ) {
                distributions.keys.forEach { value ->
                    DropdownMenuItem(onClick = {
                        chosenDistribution.value = distributions[value]!!
                        expanded = false
                    }) {
                        Text(text = value, color = Theme.colorPalette.onSecondary)
                    }
                }
            }
        }
    }
}