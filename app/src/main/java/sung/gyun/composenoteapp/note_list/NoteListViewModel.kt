package sung.gyun.composenoteapp.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import sung.gyun.composenoteapp.data.NoteEntity
import sung.gyun.composenoteapp.data.toEntities
import sung.gyun.composenoteapp.database.NoteRepository
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val readAllState = MutableStateFlow<List<NoteEntity>>(listOf())
    val _readAllState get() = readAllState

    fun getAll() {
        viewModelScope.launch(Dispatchers.IO) {
            readAllState.value = repository.getAll().toEntities()
        }
    }

}