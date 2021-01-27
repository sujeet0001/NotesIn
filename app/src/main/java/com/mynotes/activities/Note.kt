package com.mynotes.activities

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.mynotes.R
import com.mynotes.dialogs.MyAlert
import com.mynotes.dialogs.ProgressCircle
import com.mynotes.models.NoteI
import com.mynotes.utils.BaseActivity
import com.mynotes.utils.Constants
import com.mynotes.utils.NotesDB
import kotlinx.android.synthetic.main.note.*
import kotlinx.coroutines.*

class Note : BaseActivity(){

    var isFromHome: Boolean = false
    private var scope: CoroutineScope? = null
    private val addNote = 0
    private val updateNote = 1
    private val deleteNote = 2
    var noteI: NoteI? = null
    private var deleteAlert: MyAlert? = null

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        recreateActivityOnThemeChange(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewConfigs(resources.configuration, R.layout.note)
        setViews()
        setClickListeners()
    }

    private fun setViews(){
        isFromHome = intent.getBooleanExtra(Constants.FROM_HOME, false)
        if(isFromHome){
            no_title.requestFocus()
        } else {
            no_delete.visibility = View.VISIBLE
            no_save.text = Constants.UPDATE
            noteI = intent.getSerializableExtra("Note") as NoteI
            no_title.setText(noteI?.title)
            no_content.setText(noteI?.content)
        }
    }

    private fun setClickListeners(){
        no_back.setOnClickListener { onBackPressed() }
        no_save.setOnClickListener {
            if(!isFieldsEmpty()){
                if(isFromHome){
                    actionOnNote(addNote, NoteI(no_title.text.toString(), no_content.text.toString()))
                } else {
                    val noteI = NoteI(no_title.text.toString(), no_content.text.toString())
                    noteI.id = this.noteI!!.id
                    actionOnNote(updateNote, noteI)
                }
            }
        }
        no_delete.setOnClickListener {
            if(!isFieldsEmpty()){
                if(deleteAlert == null){
                    deleteAlert = MyAlert(this, Constants.TYPE_DELETE_NOTE, Constants.MSG_DELETE_NOTE)
                }
                deleteAlert!!.show()
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        recreateActivityOnThemeChange(this)
    }

    fun actionOnNote(action: Int, noteI: NoteI){
        val progressCircle = ProgressCircle(this)
        progressCircle.show()
        scope = CoroutineScope(Dispatchers.Main)
        scope?.launch {
            val db = NotesDB.get(applicationContext)?.notesDao()
            var msg = ""
            when (action){
                addNote -> {
                    noteI.let { db?.addNote(it) }
                    msg = Constants.NOTE_ADDED
                }
                updateNote -> {
                    noteI.let { db?.updateNote(noteI) }
                    msg = Constants.NOTE_UPDATED
                }
                deleteNote -> {
                    noteI.let { db?.deleteNote(it.id) }
                    msg = Constants.NOTE_DELETED
                }
            }
            progressCircle.dismiss()
            showSnack(msg, 2000)
            delay(2000)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(scope != null){
            scope?.cancel()
        }
    }

    private fun isFieldsEmpty(): Boolean {
        var returnVal = false
        if(no_title.text.isEmpty() && no_content.text.isEmpty()){
            showSnack(Constants.TITLE_CONTENT_EMPTY, Snackbar.LENGTH_LONG)
            returnVal = true
        } else if (no_title.text.isEmpty()) {
            showSnack(Constants.TITLE_EMPTY, Snackbar.LENGTH_LONG)
            returnVal = true
        } else if (no_content.text.isEmpty()) {
            showSnack(Constants.CONTENT_EMPTY, Snackbar.LENGTH_LONG)
            returnVal = true
        }
        return returnVal
    }

}
