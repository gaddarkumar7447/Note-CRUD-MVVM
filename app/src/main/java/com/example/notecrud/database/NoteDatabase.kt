package com.example.notecrud.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNodeDao() : NoteDao

    companion object {
        @Volatile
        var instance: NoteDatabase? = null
        fun getDataBase(context: Context): NoteDatabase? {
            return instance ?: synchronized(this) {
                val inst = Room.databaseBuilder(context, NoteDatabase::class.java, "note_database").build()
                instance = inst
                instance
            }
        }
    }
}
