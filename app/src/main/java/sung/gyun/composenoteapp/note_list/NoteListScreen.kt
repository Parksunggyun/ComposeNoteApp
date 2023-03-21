package sung.gyun.composenoteapp.note_list

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import sung.gyun.composenoteapp.R
import sung.gyun.composenoteapp.navigation_drawer.NavDrawerBody
import sung.gyun.composenoteapp.ui.theme.Beige
import sung.gyun.composenoteapp.ui.theme.ComposeNoteAppTheme
import sung.gyun.composenoteapp.util.Utils
import java.util.*

@Composable
fun NoteListScreen(
    windowSize: WindowWidthSizeClass,
    title: String,
    navController: NavHostController,
    viewModel: NoteListViewModel = hiltViewModel()
) {
    val state by viewModel._readAllState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getAll()
    }

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    // BackOnPressed() 뒤로가기 두번 누르면 앱 종료
    val config = LocalConfiguration.current
    val context = LocalContext.current
    val isTablet = Utils.isLargeScreen(config)
    val gridCellCount = when (windowSize) {
        WindowWidthSizeClass.Compact -> 2
        WindowWidthSizeClass.Medium -> 3
        WindowWidthSizeClass.Expanded -> 4
        else -> 2
    }
    val paddings = if (isTablet) 32.dp else 16.dp
    Scaffold(
        modifier = Modifier,
        scaffoldState = scaffoldState,
        drawerContent = {
            NavDrawerBody(onItemClick = {
                scope.launch { scaffoldState.drawerState.close() }
                //navController.navigate() 특정 화면으로 이
            })
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        topBar = {
            TopBar(title) {
                scope.launch { scaffoldState.drawerState.open() }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                content = {
                    Icon(
                        imageVector = Icons.Filled.Create,
                        contentDescription = "Add note",
                        tint = Color.White
                    )
                },
                backgroundColor = Beige,
                onClick = {
                    // 메모 페이지로 이동
                    navController.navigate("note_detail/-1L")
                })
        },
        content = {
            Surface(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxSize(),
            ) {
                if (state.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "기록한 메모가 없습니다..."
                        )
                    }
                } else {
                    Column(
                        verticalArrangement = Arrangement.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(paddings),
                                text = "$title ${state.size}개",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                )
                            )
                        }
                        LazyVerticalGrid(
                            modifier = Modifier,
                            contentPadding = PaddingValues(paddings),
                            columns = GridCells.Fixed(gridCellCount),
                            verticalArrangement = Arrangement.spacedBy(paddings),
                            horizontalArrangement = Arrangement.spacedBy(paddings),
                        ) {
                            items(state, key = { noteItem -> noteItem.id!! }) { noteItem ->
                                Log.e("NoteListScreen", "${noteItem.id}")
                                NoteListContent(
                                    note = noteItem,
                                    noteHeight = (Utils.screenWidth(config) / gridCellCount).dp,
                                    onNoteClick = {
                                        // 메모 페이지로 이동
                                        navController.navigate("note_detail/${noteItem.id}")
                                    },
                                    onNoteLongClick = {
                                        //Toast.makeText(context, "Long clicked", Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    menuAction: () -> Unit
) {
    TopAppBar(
        modifier = Modifier,
        backgroundColor = Color.White,
        title = {
            Text(
                text = title,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = { menuAction() }) {
                Icon(Icons.Default.Menu, contentDescription = "메뉴")
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_sort_24),
                    tint = Color.Black,
                    contentDescription = "sort icon"
                )
            }
        }
    )
}

@Composable
fun BackOnPressed() {
    val context = LocalContext.current
    var backPressedState by remember { mutableStateOf(true) }
    var backPressedTime = 0L

    BackHandler(enabled = backPressedState) {
        if (System.currentTimeMillis() - backPressedTime <= 1000L) {
            // 앱 종료
            (context as Activity).finish()
        } else {
            backPressedState = true
            Toast.makeText(context, "한 번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    ComposeNoteAppTheme {
        NoteListScreen(WindowWidthSizeClass.Compact, "모든 노트", rememberNavController())
    }
}