package com.mynotes.activities

import android.content.res.Configuration
import android.os.Bundle
import com.mynotes.R
import com.mynotes.utils.BaseActivity
import com.mynotes.utils.DisplayUtils
import com.mynotes.utils.NotesDB
import kotlinx.android.synthetic.main.note.*

class Note : BaseActivity() {

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setTheme(DisplayUtils.getTheme(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(DisplayUtils.getTheme(applicationContext))
        adjustFontScale(resources.configuration)
        setContentView(R.layout.note)

        no_back.setOnClickListener { onBackPressed() }
        no_save.setOnClickListener {  }

        val db = NotesDB.getNotesDB(applicationContext)

    }
}
