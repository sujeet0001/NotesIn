package com.mynotes.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesTable")
data class NoteI(
    val title: String,
    val content: String){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}