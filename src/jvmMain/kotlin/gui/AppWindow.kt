package gui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    private val colors = darkColors(
        primary = Color(112, 54, 111),
        surface = Color(110, 52, 109),
        onSurface = Color.White,
        secondary =  Color(53, 64, 118)
    )

    @Preview
    fun initGUI() = application {
        state = rememberWindowState(placement = WindowPlacement.Floating)
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
            appWindowTitleBar()
        }
    }

    @Composable
    fun FrameWindowScope.appWindowTitleBar() = WindowDraggableArea {
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
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("isFullscreen")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        state.placement == WindowPlacement.Maximized,
                        {
                            state.placement = if (it) {
                                WindowPlacement.Maximized
                            } else {
                                WindowPlacement.Floating
                            }
                        }
                    )
                    Text("isMaximized")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(state.isMinimized, { state.isMinimized = !state.isMinimized })
                    Text("isMinimized")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    var text by remember { mutableStateOf("Hello, World!") }
                    MaterialTheme {
                        Button(onClick = {
                            text = "Hello, Desktop!"
                        }) {
                            Text(text)
                        }
                    }
                }


                Text(
                    "Position ${state.position}",
                    Modifier.clickable {
                        val position = state.position
                        if (position is WindowPosition.Absolute) {
                            state.position = position.copy(x = state.position.x + 10.dp)
                        }
                    }
                )

                Text(
                    "Size ${state.size}",
                    Modifier.clickable {
                        state.size = state.size.copy(width = state.size.width + 10.dp)
                    }
                )
                TwoColumnsLayout()

            }


        }

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
                .fillMaxWidth(0.4f)
                .padding(20.dp)
        ) {
            Row {
                Text(text = "Left Pane First Text Box", modifier = Modifier.weight(1f))
                Text(text = "Left Pane Second Text Box", modifier = Modifier.weight(1f))
            }
            Spacer(Modifier.size(20.dp))
            Column(Modifier.weight(1f).border(1.dp, color = Color.Black)) {
                Text(text = "Left Pane Radio button Box  ", modifier = Modifier.padding(start = 8.dp))
                //val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
            }
            Spacer(Modifier.size(100.dp))
            Text(text = "Left Pane bottom Text Box")
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
            Text(text = "Right Pane First Text Box")
            Spacer(Modifier.size(20.dp))
            Row {
                Text(text = "Right Pane Second Text Box", modifier = Modifier.weight(1f))
                Spacer(Modifier.size(20.dp))
                Text(text = "Right Pane Third Text Box", modifier = Modifier.weight(1f))
            }
        }
    }
}