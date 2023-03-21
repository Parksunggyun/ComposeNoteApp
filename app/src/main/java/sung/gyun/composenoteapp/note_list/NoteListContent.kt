package sung.gyun.composenoteapp.note_list

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sung.gyun.composenoteapp.data.NoteEntity
import sung.gyun.composenoteapp.ui.theme.ComposeNoteAppTheme
import sung.gyun.composenoteapp.util.Utils

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteListContent(
    modifier: Modifier = Modifier,
    note: NoteEntity,
    noteHeight: Dp,
    onNoteClick: () -> Unit,
    onNoteLongClick: () -> Unit
) {
    val shape = RoundedCornerShape(16.dp)
    val isTablet = Utils.isLargeScreen(LocalConfiguration.current)
    Card(
        modifier = Modifier.combinedClickable(
            onClick = onNoteClick,
            onLongClick = onNoteLongClick
        ),
        shape = shape,
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .height(noteHeight)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(if (isTablet) 32.dp else 16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = note.content,
                    style = TextStyle(
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp,
                    ),
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                modifier = Modifier.padding(4.dp),
                text = note.title,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = Modifier.padding(bottom = if (isTablet) 32.dp else 16.dp),
                text = note.createdDay(),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
fun NoteListContentPreview() {
    ComposeNoteAppTheme {
        NoteListContent(
            note = NoteEntity(
                1,
                "Note Title",
                "Note Content",
                3,
                "2023.03.15"
            ),
            noteHeight = 200.dp,
            onNoteClick = {},
            onNoteLongClick = {}
        )
    }
}