package com.mynotes.stuffs

import androidx.room.Dao
import androidx.room.Insert
import com.mynotes.models.NoteI

@Dao
interface NoteDao {

    fun getNote(noteI: NoteI)


    fun getAllNotes(): List<NoteI>

    @Insert
    fun addNote(noteI: NoteI)
}