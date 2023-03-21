package sung.gyun.composenoteapp.database

import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

    suspend fun getAll() = noteDao.getAll()

    suspend fun getNoteById(id: Long): Note {
        return noteDao.getById(id)
    }

    suspend fun addNote(note: Note) {
        noteDao.insert(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.update(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.delete(note)
    }

    suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }
}