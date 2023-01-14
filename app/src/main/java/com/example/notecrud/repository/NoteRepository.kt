package com.example.notecrud.repository

import androidx.lifecycle.LiveData
import com.example.notecrud.database.Note
import com.example.notecrud.database.NoteDao

class NoteRepository(private val noteDao: NoteDao) {
    val allNode : LiveData<List<Note>> = noteDao.getAllNote()

    suspend fun insertNote(note: Note){
        noteDao.insert(note)
    }

    suspend fun delete(note: Note){
        noteDao.delete(note)
    }

    suspend fun update(note: Note){
        noteDao.update(note.id, note.title, note.note)
    }
}