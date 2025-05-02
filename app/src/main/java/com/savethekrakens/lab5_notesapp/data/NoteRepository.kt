package com.savethekrakens.lab5_notesapp.data

import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotesStream(): Flow<List<Note>>

    fun getNoteStream(id: Int): Flow<Note?>

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)

}