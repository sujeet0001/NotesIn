package com.mynotes.stuffs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mynotes.models.NoteI

@Dao
interface NotesDao {

    @Query("SELECT * FROM notesTable WHERE title LIKE :title")
    fun getNote(title: String) : NoteI

    @Query("SELECT * FROM notesTable")
    fun getAllNotes(): List<NoteI>

    @Insert()
    fun addNote(noteI: NoteI)
}