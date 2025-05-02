package com.savethekrakens.lab5_notesapp

import android.app.Application
import com.savethekrakens.lab5_notesapp.data.AppContainer
import com.savethekrakens.lab5_notesapp.data.AppDataContainer

class NoteApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}