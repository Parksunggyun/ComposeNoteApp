package sung.gyun.composenoteapp.note_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import sung.gyun.composenoteapp.data.NoteEntity
import sung.gyun.composenoteapp.data.toNote
import sung.gyun.composenoteapp.data.toNoteEntity
import sung.gyun.composenoteapp.database.NoteRepository
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val repository: NoteRepository
): ViewModel() {

    private val noteState = MutableStateFlow(NoteEntity.EMPTY_NOTE)
    val _noteState get() = noteState

    fun getNoteById(noteId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNoteById(noteId)?.let {
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
}