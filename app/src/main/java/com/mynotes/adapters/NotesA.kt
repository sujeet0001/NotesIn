package com.mynotes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mynotes.R
import com.mynotes.activities.Home
import com.mynotes.models.NoteI

class NotesA(private val context: Context, private val notes: ArrayList<NoteI>): RecyclerView.Adapter<NotesA.NotesH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesH {
        return NotesH(LayoutInflater.from(parent.context).inflate(R.layout.note_i, parent, false))
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NotesH, position: Int) {
        val noteI: NoteI = notes[position]
        holder.title.text = noteI.title
        holder.content.text = noteI.content
        holder.itemView.setOnClickListener {
            (context as Home).goToNote(noteI)
        }
    }

    class NotesH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.noi_title)
        val content: TextView = itemView.findViewById(R.id.noi_content)
    }
}