package sung.gyun.composenoteapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import sung.gyun.composenoteapp.database.NoteDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NoteModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "note_list_db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideNoteDatabaseDao(noteDatabase: NoteDatabase) = noteDatabase.noteDao()

}