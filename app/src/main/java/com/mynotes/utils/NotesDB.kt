package com.mynotes.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mynotes.models.NoteI

@Database(entities = [NoteI::class], version = 1)
abstract class NotesDB : RoomDatabase() {

    abstract fun notesDao(): NotesDao

    companion object {
        @Volatile private var INSTANCE: NotesDB? = null
        fun get(context: Context): NotesDB? {
            if (INSTANCE == null) {
                synchronized(NotesDB::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        NotesDB::class.java,
                        "notesDB"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}