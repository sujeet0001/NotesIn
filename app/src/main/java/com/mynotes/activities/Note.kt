package com.mynotes.activities

import android.os.Bundle
import androidx.room.Room
import com.mynotes.R
import com.mynotes.stuffs.BaseActivity
import com.mynotes.stuffs.NotesDB
import kotlinx.android.synthetic.main.note.*

class Note : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adjustFontScale(resources.configuration)
        setContentView(R.layout.note)

        no_discard.setOnClickListener { onBackPressed() }
        no_save.setOnClickListener {  }

        val db = NotesDB.getNotesDB(applicationContext)


    }
}
