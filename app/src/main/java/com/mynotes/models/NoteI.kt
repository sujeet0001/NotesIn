package com.mynotes.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notesTable")
data class NoteI(
    val title: String,
    val content: String): Serializable{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}