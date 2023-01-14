package com.example.notecrud.model

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notecrud.database.Note
import com.example.notecrud.database.NoteDatabase
import com.example.notecrud.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application :Application) : AndroidViewModel(application) {

    private val repository : NoteRepository
    val allNode : LiveData<List<Note>>

    init {
        val dao = NoteDatabase.getDataBase(application)?.getNodeDao()
        repository = dao?.let { NoteRepository(it) }!!
        allNode = repository.allNode

    }

    fun deleteNote(note : Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun insertNote(note : Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNote(note)
    }

    fun updateNote(note : Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }


}