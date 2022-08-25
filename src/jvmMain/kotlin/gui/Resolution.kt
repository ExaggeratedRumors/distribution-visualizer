package gui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

//@Composable
//private fun resolutionManager() {
//    Surface(
//        modifier = Modifier.fillMaxSize().padding(0.dp, 40.dp, 0.dp, 0.dp)
//            .shadow(15.dp, RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp), true),
//        color = colors.secondary,
//        shape = RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp)
//    ) {
//        Column {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Text("isFullscreen")
//            }
//
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Checkbox(
//                    state.placement == WindowPlacement.Maximized,
//                    {
//                        state.placement = if (it) {
//                            WindowPlacement.Maximized
//                        } else {
//                            WindowPlacement.Floating
//                        }
//                    }
//                )
//                Text("isMaximized")
//            }
//
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Checkbox(state.isMinimized, { state.isMinimized = !state.isMinimized })
//                Text("isMinimized")
//            }
//
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                var text by remember { mutableStateOf("Hello, World!") }
//                MaterialTheme {
//                    Button(onClick = {
//                        text = "Hello, Desktop!"
//                    }) {
//                        Text(text)
//                    }
//                }
//            }
//
//
//            Text(
//                "Position ${state.position}",
//                Modifier.clickable {
//                    val position = state.position
//                    if (position is WindowPosition.Absolute) {
//                        state.position = position.copy(x = state.position.x + 10.dp)
//                    }
//                }
//            )
//
//            Text(
//                "Size ${state.size}",
//                Modifier.clickable {
//                    state.size = state.size.copy(width = state.size.width + 10.dp)
//                }
//            )
//        }
//    }
//}