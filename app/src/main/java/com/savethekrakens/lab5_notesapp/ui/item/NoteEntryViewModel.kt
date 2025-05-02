package com.savethekrakens.lab5_notesapp.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.savethekrakens.lab5_notesapp.data.Note
import com.savethekrakens.lab5_notesapp.data.NoteRepository

class NoteEntryViewModel(private val noteRepository: NoteRepository) : ViewModel(){
    var noteUiState by mutableStateOf(NoteUiState())
        private set

    fun updateUiState(noteDetails: NoteDetails){
        noteUiState =
            NoteUiState(noteDetails = noteDetails, isEntryValid = validateInput(noteDetails))
    }

    suspend fun saveNote(){
        if (validateInput()){
            noteRepository.insertNote(noteUiState.noteDetails.toNote())
        }
    }

    private fun validateInput(uiState: NoteDetails = noteUiState.noteDetails): Boolean{
        return with(uiState) {
            title.isNotBlank() && content.isNotBlank()
        }
    }
}

data class NoteUiState(
    val noteDetails: NoteDetails = NoteDetails(),
    val isEntryValid: Boolean = false
)

data class NoteDetails(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val timestamp: Int = 0
)

fun NoteDetails.toNote(): Note = Note(
    id = id,
    title = title,
    content = content,
    timestamp = timestamp
)

fun Note.toNoteUiState(isEntryValid: Boolean = false): NoteUiState = NoteUiState(
    noteDetails = this.toNoteDetails(),
    isEntryValid = isEntryValid
)

fun Note.toNoteDetails(): NoteDetails = NoteDetails(
    id = id,
    title = title,
    content = content,
    timestamp = timestamp
)