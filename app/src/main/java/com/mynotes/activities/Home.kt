package com.mynotes.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.mynotes.R
import com.mynotes.adapters.NotesA
import com.mynotes.models.NoteI
import com.mynotes.utils.BaseActivity
import kotlinx.android.synthetic.main.home.*

class Home : BaseActivity() {

    private var notes = ArrayList<NoteI>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adjustFontScale(resources.configuration)
        setContentView(R.layout.home)

        ho_change_secret_code.setOnClickListener{startActivity(Intent(applicationContext, ChangeSecretCode::class.java))}
        ho_add.setOnClickListener{startActivity(Intent(applicationContext, Note::class.java))}

        initList()

        checkForNotesAvailability()
    }

    private fun initList(){
        ho_list.setHasFixedSize(true)
        ho_list.layoutManager = (LinearLayoutManager(applicationContext))
        ho_list.adapter = NotesA(notes)
    }

    private fun checkForNotesAvailability(){
        if(notes.size == 0){
            ho_no_notes.visibility = View.VISIBLE
        } else {
            ho_no_notes.visibility = View.GONE
        }
    }
}
