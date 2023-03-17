package sung.gyun.composenoteapp.note_detail

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import sung.gyun.composenoteapp.data.NoteEntity
import sung.gyun.composenoteapp.ui.theme.ComposeNoteAppTheme
import sung.gyun.composenoteapp.ui.theme.DarkBeige
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    noteId: Long? = null,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: NoteDetailViewModel = hiltViewModel()
) {
    val state = viewModel._noteState.collectAsState()

    var title by remember {
        mutableStateOf("")
    }
    var content by remember {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = true) {
        if (noteId != null) {
            viewModel.getNoteById(noteId)
            title = state.value.title
            content = state.value.content
        }
    }

    val context = LocalContext.current
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(navController, title = if (state.value.id == -1L) "새 메모" else "메모 수정") {
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN)
                state.value.title = title
                state.value.content = content
                if (state.value.title.isEmpty()) {
                    Toast.makeText(context, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    // Room 에 저장 및 뒤로가기
                    if(noteId == -1L) {
                        val saveNote = NoteEntity(
                            title = state.value.title,
                            content = state.value.content,
                            priority = 2,
                            createAt = sdf.format(Date())
                        )
                        viewModel.saveNote(saveNote)
                    } else {
                        val saveNote = NoteEntity(
                            id = noteId,
                            title = state.value.title,
                            content = state.value.content,
                            priority = 2,
                            createAt = sdf.format(Date())
                        )
                        viewModel.updateNote(saveNote)
                    }
                    navController.popBackStack()
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding()),
        ) {
            BasicTextField(
                modifier = Modifier
                    .height(
                        (it
                            .calculateTopPadding()
                            .times(0.75f))
                    ),
                value = title,
                onValueChange = { t ->
                    title = t
                },
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray,
                ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if(title.isEmpty()) {
                            Text(
                                text = "제목을 입력해주세요...",
                                style = HintTextStyle(),
                            )
                        }
                        innerTextField()
                    }
                }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = content,
                placeholder = {
                    Text(
                        text = "메모 내용을 입력해주세요...",
                        style = HintTextStyle(),
                    )
                },
                onValueChange = { c ->
                    content = c
                })
        }
    }
}

fun HintTextStyle() = TextStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.Normal,
    color = Color.LightGray
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavHostController,
    title: String,
    onSaveNoteClick: () -> Unit,
) {
    CenterAlignedTopAppBar(
        modifier = Modifier,
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "뒤로가기",
                    tint = DarkBeige
                )
            }
        },
        actions = {
            IconButton(onClick = {
                onSaveNoteClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    tint = DarkBeige,
                    contentDescription = "저장"
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun NoteDetailScreenPreview() {
    ComposeNoteAppTheme {
        NoteDetailScreen(noteId = -1L, rememberNavController(), Modifier)
    }
}