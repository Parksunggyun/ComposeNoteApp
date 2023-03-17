package sung.gyun.composenoteapp.navigation_drawer

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun NavDrawerBody(onItemClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Home()
        Info()
        Settings()
    }
}

@Composable
fun Home() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Icon(imageVector = Icons.Filled.Home, contentDescription = "Home")
        Text(text = "Home")
    }
}

@Composable
fun Info() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Icon(imageVector = Icons.Filled.Info, contentDescription = "Info")
        Text(text = "Info")
    }
}

@Composable
fun Settings() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
        Text(text = "Settings")
    }
}