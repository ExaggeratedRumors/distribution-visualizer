package gui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import kotlin.system.exitProcess

class AppWindow {
    private lateinit var state : WindowState

    fun initGUI() = application {
        state = rememberWindowState(placement = WindowPlacement.Floating)
        Window(
            onCloseRequest = { exitApplication() },
            title = Strings.appName,
            transparent = true,
            icon = painterResource(Strings.iconPath),
            undecorated = true,
            state = state,
            resizable = false
        ) {
            Theme.VisualizerTheme {  }
            createWindow()
            createAppWindowBar()
        }
    }

    @Composable
    fun FrameWindowScope.createAppWindowBar() = WindowDraggableArea {
        MaterialTheme (colors = Theme.colorPalette) {
            Surface(
                modifier = Modifier.fillMaxWidth().height(40.dp),
                shape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = Strings.appName,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(150.dp),
                        fontFamily = Typography.mainTypography.body1.fontFamily,
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
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 40.dp, 0.dp, 0.dp)
                .shadow(15.dp, RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp), true),
            color = Theme.colorPalette.secondary,
            shape = RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp)
        ) { AppLayout().twoColumnsLayout() }
    }
}