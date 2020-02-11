package com.mynotes.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteI(
    val title: String,
    val content: String)