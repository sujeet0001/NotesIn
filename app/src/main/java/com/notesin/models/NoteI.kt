package com.notesin.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notesTable")
data class NoteI(
    @PrimaryKey val id: Int,
    val title: String,
    val content: String): Serializable