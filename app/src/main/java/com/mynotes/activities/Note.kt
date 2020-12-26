package com.mynotes.activities

import android.os.Bundle
import com.mynotes.R
import com.mynotes.utils.BaseActivity
import com.mynotes.utils.NotesDB
import kotlinx.android.synthetic.main.note.*

class Note : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme()
        adjustFontScale(resources.configuration)
        setContentView(R.layout.note)

        no_back.setOnClickListener { onBackPressed() }
        no_save.setOnClickListener {  }

        val db = NotesDB.getNotesDB(applicationContext)

    }
}
