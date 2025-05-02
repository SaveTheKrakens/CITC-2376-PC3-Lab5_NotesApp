package com.savethekrakens.lab5_notesapp.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.savethekrakens.lab5_notesapp.NoteApplication
import com.savethekrakens.lab5_notesapp.ui.home.HomeScreenViewModel
import com.savethekrakens.lab5_notesapp.ui.item.NoteDetailsViewModel
import com.savethekrakens.lab5_notesapp.ui.item.NoteEditViewModel
import com.savethekrakens.lab5_notesapp.ui.item.NoteEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            NoteEditViewModel(
                this.createSavedStateHandle(),
                noteApplication().container.noteRepository
            )
        }
        initializer {
            NoteEntryViewModel(noteApplication().container.noteRepository)
        }
        initializer {
            NoteDetailsViewModel(
                this.createSavedStateHandle(),
                noteApplication().container.noteRepository
            )
        }
        initializer {
            HomeScreenViewModel(noteApplication().container.noteRepository)
        }
    }

    fun CreationExtras.noteApplication(): NoteApplication =
        (this[AndroidViewModelFactory.APPLICATION_KEY] as NoteApplication)
}