package com.example.notecrud.database

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query
import androidx.room.OnConflictStrategy
import androidx.room.Dao


@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("UPDATE notes_table Set title = :title, note = :note WHERE id = :id")
    suspend fun update(id : Int?, title : String?, note : String? )


    @Query("SELECT * notes_table order by id ASC")
    fun getAllNote() : LiveData<List<Note>>

}