package gui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class AppLayout {
    private lateinit var chosenDistribution : String
    private lateinit var showGrid : MutableState<Boolean>

    @Composable
    fun twoColumnsLayout() {
        showGrid = remember { mutableStateOf(true) }
        Row(Modifier.fillMaxSize()) {
            LeftPaneContent()
            Divider(
                color = Color.Blue,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            RightPaneContent()
        }
    }

    @Composable
    fun LeftPaneContent() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.7f)
                .padding(20.dp)
        ) {
            Row {
                Text(text = "Chosen distribution:", modifier = Modifier.weight(1f))
                Text(text = "Left Pane Second Text Box", modifier = Modifier.weight(1f))
            }

            Spacer(Modifier.size(20.dp))
            Column(
                Modifier.weight(1f).border(1.dp, color = Color.Black).fillMaxWidth(0.7f)
            ) {

            }
            Spacer(Modifier.size(20.dp))
            Row (verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    showGrid.value,
                    { showGrid.value = it },
                    enabled = true,
                    colors = CheckboxDefaults.colors(Color.Green)
                )
                Text("Show Grid")
            }
        }
    }

    @Composable
    fun RightPaneContent() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(text = "Input mean")
            Spacer(Modifier.size(20.dp))
            Row {
                Text(text = "Input SD", modifier = Modifier.weight(1f))
                Spacer(Modifier.size(20.dp))
                Text(text = "[ ]", modifier = Modifier.weight(1f))
            }
        }
    }
}