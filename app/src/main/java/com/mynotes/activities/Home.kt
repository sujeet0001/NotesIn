package com.mynotes.activities

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mynotes.R
import com.mynotes.adapters.NotesA
import com.mynotes.models.NoteI
import com.mynotes.stuffs.BaseActivity
import kotlinx.android.synthetic.main.home.*

class Home : BaseActivity() {

    private var notes = ArrayList<NoteI>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adjustFontScale(resources.configuration)
        setContentView(R.layout.home)

        ho_chngpasscode.setOnClickListener{startActivity(Intent(applicationContext, ChangePasscode::class.java))}
        ho_add.setOnClickListener{startActivity(Intent(applicationContext, Note::class.java))}

        notes.add(NoteI("guygguguy", "guckclucuvuuliu"))
        notes.add(NoteI("guygguguy", "guckclucuvuuliu"))
        notes.add(NoteI("guygguguy", "guckclucuvuuliu"))
        notes.add(NoteI("guygguguy", "guckclucuvuuliu"))
        notes.add(NoteI("guygguguy", "guckclucuvuuliu"))
        notes.add(NoteI("guygguguy", "guckclucuvuuliu"))
        notes.add(NoteI("guygguguy", "guckclucuvuuliu"))
        notes.add(NoteI("guygguguy", "guckclucuvuuliu"))

        initList()
    }

    private fun initList(){
        ho_list.setHasFixedSize(true)
        ho_list.layoutManager = (LinearLayoutManager(applicationContext))
        ho_list.adapter = (NotesA(notes))
    }
}
