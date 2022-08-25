package gui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import kotlin.system.exitProcess

class AppWindow {
    private lateinit var state : WindowState
    private lateinit var chosenDistribution : String
    private lateinit var showGrid : MutableState<Boolean>

    private val colors = darkColors(
        primary = Color(112, 54, 111),
        surface = Color(110, 52, 109),
        onSurface = Color.White,
        secondary =  Color(53, 64, 118)
    )

    @Preview
    fun initGUI() = application {
        state = rememberWindowState(placement = WindowPlacement.Floating)
        showGrid = remember { mutableStateOf(true) }
        Window(
            onCloseRequest = { exitApplication() },
            title = Utils.appName,
            transparent = true,
            icon = painterResource(Utils.iconPath),
            undecorated = true,
            state = state,
            resizable = false
        ) {
            createWindow()
            createAppWindowBar()
        }
    }

    @Composable
    fun FrameWindowScope.createAppWindowBar() = WindowDraggableArea {
        MaterialTheme (colors = colors) {
            Surface(
                modifier = Modifier.fillMaxWidth().height(40.dp),
                shape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = Utils.appName,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(150.dp),
                        fontFamily = Utils.font,
                    )
                    Spacer(Modifier.size(200.dp))
                    Button(onClick = {
                        state.isMinimized = !state.isMinimized
                    }) {
                        Text("_")
                    }
                    Button(onClick = {
                        exitProcess(0)
                    }) {
                        Text("X")
                    }
                }
            }
        }
    }

    @Composable
    private fun createWindow() {
        Surface(
            modifier = Modifier.fillMaxSize().padding(0.dp, 40.dp, 0.dp, 0.dp).shadow(15.dp, RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp), true),
            color = colors.secondary,
            shape = RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp)
        ) { TwoColumnsLayout() }
    }


    @Composable
    fun TwoColumnsLayout() {
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