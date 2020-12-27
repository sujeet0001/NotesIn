package com.mynotes.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mynotes.R
import com.mynotes.adapters.NotesA
import com.mynotes.models.NoteI
import com.mynotes.utils.BaseActivity
import com.mynotes.utils.DisplayUtils
import kotlinx.android.synthetic.main.home.*

class Home : BaseActivity() {

    private var notes = ArrayList<NoteI>()

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setTheme(DisplayUtils.getTheme(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(DisplayUtils.getTheme(applicationContext))
        adjustFontScale(resources.configuration)
        setContentView(R.layout.home)

        ho_settings.setOnClickListener{startActivity(Intent(applicationContext, Settings::class.java))}
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
