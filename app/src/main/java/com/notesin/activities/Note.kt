package com.notesin.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.notesin.R
import com.notesin.dialogs.MyAlert
import com.notesin.models.NoteI
import com.notesin.utils.BaseActivity
import com.notesin.utils.Constants
import com.notesin.utils.NotesDB
import kotlinx.android.synthetic.main.note.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class Note : BaseActivity(){

    var isAddNote: Boolean = false
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
        isAddNote = intent.getBooleanExtra(Constants.FROM_ADD_NOTE, false)
        if(isAddNote){
            no_title.requestFocus()
        } else {
            no_delete.visibility = View.VISIBLE
            noteI = intent.getSerializableExtra("Note") as NoteI
            no_title.setText(noteI?.title)
            no_content.setText(noteI?.content)
        }
    }

    private fun setClickListeners(){
        no_back.setOnClickListener { onBackPressed() }
        no_share.setOnClickListener {
            if(!isFieldsEmpty()){
                val shareIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, no_title.text.toString() + "\n\n" + no_content.text.toString())
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(shareIntent, null))
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
        scope = CoroutineScope(Dispatchers.IO)
        scope?.launch {
            val db = NotesDB.get(applicationContext)?.notesDao()
            when (action){
                addNote -> {
                    noteI.let { db?.addNote(it) }
                    storeUniqueId()
                }
                updateNote -> {
                    noteI.let { db?.updateNote(noteI) }
                }
                deleteNote -> {
                    noteI.let { db?.deleteNote(it.id) }
                    finish()
                }
            }
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

    override fun onPause() {
        super.onPause()
        if(no_title.text.isNotEmpty() && no_content.text.isNotEmpty()){
            if(isAddNote){
                isAddNote = false
                this.noteI = NoteI(getUniqueId(), no_title.text.toString(), no_content.text.toString())
                actionOnNote(addNote, this.noteI!!)
            } else {
                val noteI = NoteI(this.noteI!!.id, no_title.text.toString(), no_content.text.toString())
                actionOnNote(updateNote, noteI)
            }
        }
    }

}
