package com.mynotes.activities

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import com.mynotes.R
import com.mynotes.utils.BaseActivity
import com.mynotes.utils.Constants
import com.mynotes.utils.NotesDB
import kotlinx.android.synthetic.main.note.*

class Note : BaseActivity() {

    var isFromHome: Boolean = false

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        recreateActivityOnThemeChange(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewConfigs(resources.configuration, R.layout.note)
        setViews()
        setClickListeners()
        val db = NotesDB.get(applicationContext)
    }

    private fun setViews(){
        isFromHome = intent.getBooleanExtra(Constants.FROM_HOME, false)
        if(isFromHome){
            no_title.requestFocus()
        } else {
            no_delete.visibility = View.VISIBLE
            no_save.text = Constants.UPDATE
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
