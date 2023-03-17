package sung.gyun.composenoteapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    @Query("SELECT * from note_list")
    suspend fun getAll(): List<Note>

    @Query("SELECT * from note_list where itemId = :id")
    suspend fun getById(id: Long): Note

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("DELETE FROM note_list")
    suspend fun deleteAllNotes()
}