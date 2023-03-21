package sung.gyun.composenoteapp.note_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sung.gyun.composenoteapp.data.NoteEntity
import sung.gyun.composenoteapp.data.toNote
import sung.gyun.composenoteapp.data.toNoteEntity
import sung.gyun.composenoteapp.database.NoteRepository
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val noteState = MutableStateFlow(NoteEntity())
    val _noteState: StateFlow<NoteEntity> = noteState.asStateFlow()

    fun getNoteById(noteId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNoteById(noteId).let {
                noteState.value = it.toNoteEntity()
            }
        }
    }

    fun saveNote(noteEntity: NoteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(noteEntity.toNote())
        }
    }

    fun updateNote(noteEntity: NoteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(noteEntity.toNote())
        }
    }

    fun updateTitle(title: String) {
        noteState.update {
            it.copy(title = title)
        }
    }

    fun updateContent(content: String) {
        noteState.update {
            it.copy(content = content)
        }
    }
}