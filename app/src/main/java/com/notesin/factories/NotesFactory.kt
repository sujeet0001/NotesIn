package com.notesin.factories

import android.content.Context
import com.notesin.models.NoteI
import com.notesin.utils.NotesDB

class NotesFactory {

    companion object {

        suspend fun getAllNotes(ctx: Context): ArrayList<NoteI> {
            return NotesDB.get(ctx)?.notesDao()?.getAllNotes() as ArrayList
        }

        suspend fun addNote(ctx: Context, noteI: NoteI){
            NotesDB.get(ctx)?.notesDao()?.addNote(noteI)
        }

        suspend fun deleteNote(ctx: Context, id: Int){
            NotesDB.get(ctx)?.notesDao()?.deleteNote(id)
        }

        suspend fun updateNote(ctx: Context, noteI: NoteI){
            NotesDB.get(ctx)?.notesDao()?.updateNote(noteI)
        }

    }

}