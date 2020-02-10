package com.mynotes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.mynotes.R
import com.mynotes.models.NoteI

class NotesA(private val context: Context, private val notes: ArrayList<NoteI>): RecyclerView.Adapter<NotesA.NotesH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesH {
        return NotesH(LayoutInflater.from(context).inflate(R.layout.note_i, parent, false))
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NotesH, position: Int) {

    }

    class NotesH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}