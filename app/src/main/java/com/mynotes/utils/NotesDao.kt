package com.mynotes.utils

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mynotes.models.NoteI

@Dao
interface NotesDao {

    @Query("SELECT * FROM notesTable WHERE id LIKE :id")
    suspend fun getNote(id: Int): NoteI

    @Query("SELECT * FROM notesTable")
    suspend fun getAllNotes(): List<NoteI>

    @Insert
    suspend fun addNote(noteI: NoteI)

    @Query("DELETE FROM notesTable WHERE id LIKE :id")
    suspend fun deleteNote(id: Int)

    @Update
    suspend fun updateNote(noteI: NoteI)

}