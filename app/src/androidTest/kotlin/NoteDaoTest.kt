import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.savethekrakens.lab5_notesapp.data.Note
import com.savethekrakens.lab5_notesapp.data.NoteDao
import com.savethekrakens.lab5_notesapp.data.NoteDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class NoteDaoTest {
    private lateinit var noteDao: NoteDao
    private lateinit var noteDatabase: NoteDatabase

    private var noteTest1 = Note(1, "This is a title", "This is some content and whatnot y'all", 1)
    private var noteTest2 = Note(2, "This is a title again", "This is some content and whatnot y'all again", 4)
    private var noteTest3 = Note(3, "This is a title again and again", "This is some content and whatnot y'all over and over", 8)

    private suspend fun addOneNoteToDb(){
        noteDao.insert(noteTest1)
    }

    private suspend fun addThreeNoteToDb(){
        noteDao.insert(noteTest1)
        noteDao.insert(noteTest2)
        noteDao.insert(noteTest3)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsNoteIntoDB() = runBlocking {
        addOneNoteToDb()
        val allNotes = noteDao.getAllNotes().first()
        assertEquals(allNotes[0], noteTest1)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdate_updatesNoteInDB() = runBlocking {
        addOneNoteToDb()
        noteDao.update(Note(1, "This is new", "So new", 7))
        val allNotes = noteDao.getAllNotes().first()
        assertEquals(allNotes[0], Note(1, "This is new", "So new", 7))
    }

    @Test
    @Throws(Exception::class)
    fun daoDelete_deletesNoteInDB() = runBlocking {
        addOneNoteToDb()
        noteDao.delete(noteTest1)
        val allNotes = noteDao.getAllNotes().first()
        assertTrue(allNotes.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllNotes_returnsAllNotesFromDB() = runBlocking {
        addThreeNoteToDb()
        val allNotes = noteDao.getAllNotes().first()
        assertEquals(allNotes[0], noteTest1)
        assertEquals(allNotes[1], noteTest2)
        assertEquals(allNotes[2], noteTest3)
    }

    @Before
    fun createDb(){
        val context: Context = ApplicationProvider.getApplicationContext()
        noteDatabase = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        noteDao = noteDatabase.noteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        noteDatabase.close()
    }
}