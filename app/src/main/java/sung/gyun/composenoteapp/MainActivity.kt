package sung.gyun.composenoteapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import sung.gyun.composenoteapp.note_detail.NoteDetailScreen
import sung.gyun.composenoteapp.note_list.NoteListScreen
import sung.gyun.composenoteapp.ui.theme.ComposeNoteAppTheme
import sung.gyun.composenoteapp.util.Utils

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNoteAppTheme {
                val config = LocalConfiguration.current
                val isPortrait = Utils.isPortrait(config.orientation)
                val navController = rememberAnimatedNavController()
                val windowSize = calculateWindowSizeClass(activity = this)
                AnimatedNavHost(navController = navController, startDestination = "note_list") {
                    composable(
                        route = "note_list"
                    ) {
                        NoteListScreen(
                            windowSize = windowSize.widthSizeClass,
                            title = "전체",
                            navController = navController
                        )
                    }
                    composable(
                        route = "note_detail/{noteId}",
                        enterTransition = {
                            slideInVertically(initialOffsetY = { it }, animationSpec = tween(400))
                        },
                        exitTransition = {
                            slideOutVertically(targetOffsetY = { it }, animationSpec = tween(400))
                        },
                        arguments = listOf(
                            navArgument("noteId") {
                                type = NavType.LongType
                                defaultValue = -1L
                            }
                        )
                    ) { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getLong("noteId") ?: -1L
                        Log.e("mainActivty", "noteId = $noteId")
                        NoteDetailScreen(noteId = noteId, navController = navController)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeNoteAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            NoteListScreen(
                windowSize = WindowWidthSizeClass.Compact,
                title = "All Notes",
                rememberNavController()
            )
        }
    }
}