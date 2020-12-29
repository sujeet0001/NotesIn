package com.mynotes.activities

import android.content.res.Configuration
import android.os.Bundle
import com.mynotes.R
import com.mynotes.utils.BaseActivity
import com.mynotes.utils.Constants
import com.mynotes.utils.DisplayUtils
import com.mynotes.utils.NotesDB
import kotlinx.android.synthetic.main.note.*

class Note : BaseActivity() {

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        recreateActivityOnThemeChange(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewConfigs(resources.configuration)
        setContentView(R.layout.note)
        setViews()
        setClickListeners()
        val db = NotesDB.getNotesDB(applicationContext)
    }

    private fun setViews(){
        if(intent.getBooleanExtra(Constants.FROM_HOME, false)){
            no_title.requestFocus()
        }
    }

    private fun setClickListeners(){
        no_back.setOnClickListener { onBackPressed() }
        no_save.setOnClickListener {  }
    }

    override fun onRestart() {
        super.onRestart()
        recreateActivityOnThemeChange(this)
    }
}
