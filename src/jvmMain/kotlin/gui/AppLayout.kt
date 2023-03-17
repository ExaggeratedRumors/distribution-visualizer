package gui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.*
import model.Distribution
import model.Gauss

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
                Text (text = "Chosen distribution: ${chosenDistribution.value::class.simpleName}", modifier = Modifier.weight(1f))
            }

            Spacer (Modifier.size(20.dp))
            Column (
                Modifier.weight(1f)
                    .border(1.dp, color = Theme.colorPalette.primary)
                    .fillMaxWidth(0.7f)
                    .background(color = Theme.colorPalette.onSurface),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val plotPane = PlotPane()
                plotPane.printPlotPane(chosenDistribution.value)
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
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text (text = "Input mean")
            Spacer (Modifier.size(20.dp))
            Row {
                switchDistribution()
                Text (text = "Input SD", modifier = Modifier.weight(1f))
                Spacer (Modifier.size(20.dp))
                Text (text = "[ ]", modifier = Modifier.weight(1f))
            }
            ExtendedFloatingActionButton(
                onClick = { chosenDistribution.value = if (chosenDistribution.value is Gauss) Poisson(1f) else Gauss(0f, 1f) },
            text = { Text(text = "Change distribution") }
            )
        }
    }

    @Composable
    fun switchDistribution() {
        var expanded by remember { mutableStateOf(false) }
        var disabledValue = chosenDistribution.value::class.simpleName!!
        Box(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
        ) {
            Text(
                chosenDistribution.value::class.simpleName!!,
                modifier = Modifier
                    .size(200.dp, 45.dp)
                    .clickable(onClick = { expanded = true }).background(Color.Gray)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .size(200.dp, 500.dp)
                    .background(Theme.colorPalette.primary)
            ) {
                distributions.keys.forEach { value ->
                    DropdownMenuItem(onClick = {
                            chosenDistribution.value = distributions[value]!!
                            expanded = false
                        }) {
                        val disabledText = if(value == disabledValue) {
                            " (Disabled)"
                        } else {
                            ""
                        }
                        Text(text = value + disabledText)
                    }
                }
            }
        }
    }

    @Composable
    fun DropdownDemo() {
        var expanded by remember { mutableStateOf(false) }
        val items = listOf("A", "B", "C", "D", "E", "F")
        val disabledValue = "B"
        var selectedIndex by remember { mutableStateOf(0) }
        Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.TopStart)) {
            Text(items[selectedIndex],modifier = Modifier.size(300.dp, 45.dp).clickable(onClick = { expanded = true }).background(
                Color.Gray))
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .size(300.dp,700.dp)
                    .background(Color.Red)
            ) {
                items.forEachIndexed { index, s ->
                    DropdownMenuItem(onClick = {
                        selectedIndex = index
                        expanded = false
                    }) {
                        val disabledText = if (s == disabledValue) {
                            " (Disabled)"
                        } else {
                            ""
                        }
                        Text(text = s + disabledText)
                    }
                }
            }
        }
    }
}