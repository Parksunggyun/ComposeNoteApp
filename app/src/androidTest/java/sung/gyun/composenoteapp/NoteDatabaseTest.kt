package sung.gyun.composenoteapp

import android.util.Log
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import sung.gyun.composenoteapp.data.Priority
import sung.gyun.composenoteapp.database.Note
import sung.gyun.composenoteapp.database.NoteDatabase
import sung.gyun.composenoteapp.database.NoteDao
import java.io.IOException
import javax.inject.Inject

@HiltAndroidTest
class NoteDatabaseTest {

    @get:Rule val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var noteDao: NoteDao

    @Inject
    lateinit var db: NoteDatabase

    @Before
    fun initDatabase() {
        hiltRule.inject()
    }

    @After
    @Throws(IOException::class)
    fun deleteDatabase() {
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun insertAndGetTodo() = runBlocking {
        val noteItem = Note(
            1L,
            "title",
            "content",
            Priority.MIDDLE.value,
            "2023-03-17"
        )
        noteDao.insert(noteItem)
        val oneItem = noteDao.getById(1L)
        Log.e("oneItem id", "${oneItem?.itemId}")
        assertEquals(oneItem?.itemId, 1L)
    }
}