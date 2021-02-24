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

class Note : BaseActivity() {

    var isAddNote: Boolean = false
    private var scope: CoroutineScope? = null
    private val addNote = 0
    private val updateNote = 1
    private val deleteNote = 2
    var noteI: NoteI? = null
    private var myAlert: MyAlert? = null
    private var field = ""

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

    private fun setViews() {
        isAddNote = intent.getBooleanExtra(Constants.FROM_ADD_NOTE, false)
        if (isAddNote) {
            no_title.requestFocus()
        } else {
            no_delete.visibility = View.VISIBLE
            noteI = intent.getSerializableExtra("Note") as NoteI
            no_title.setText(noteI?.title)
            no_content.setText(noteI?.content)
        }
    }

    private fun setClickListeners() {
        no_back.setOnClickListener { onBackPressed() }
        no_share.setOnClickListener {
            field = fieldsType()
            if (field == Constants.NOT_EMPTY) {
                val shareIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT,
                        no_title.text.toString() + "\n\n" + no_content.text.toString())
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(shareIntent, null))
            } else {
                showSnack(field + Constants.SHOULD_NOT_BE_EMPTY, Snackbar.LENGTH_LONG)
            }
        }
        no_delete.setOnClickListener {
            if (myAlert == null) {
                myAlert = MyAlert(this, Constants.TYPE_DELETE_NOTE, Constants.MSG_DELETE_NOTE)
            } else {
                myAlert!!.setType(Constants.TYPE_DELETE_NOTE)
                myAlert!!.setMessage(Constants.MSG_DELETE_NOTE)
            }
            myAlert!!.show()
        }
    }

    override fun onRestart() {
        super.onRestart()
        recreateActivityOnThemeChange(this)
    }

    fun actionOnNote(action: Int, noteI: NoteI) {
        if(scope == null){
            scope = CoroutineScope(Dispatchers.IO)
        }
        scope?.launch {
            val db = NotesDB.get(applicationContext)?.notesDao()
            when (action) {
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
        if (scope != null) {
            scope?.cancel()
        }
    }

    private fun fieldsType(): String {
        if (no_title.text.isEmpty() && no_content.text.isEmpty()) {
            return Constants.NOTE_TITLE_CONTENT
        } else if (no_title.text.isEmpty()) {
            return Constants.NOTE_TITLE
        } else if (no_content.text.isEmpty()) {
            return Constants.NOTE_CONTENT
        }
        return Constants.NOT_EMPTY
    }

    override fun onPause() {
        super.onPause()
        if (no_title.text.isNotEmpty() && no_content.text.isNotEmpty()) {
            if (isAddNote) {
                isAddNote = false
                this.noteI =
                    NoteI(getUniqueId(), no_title.text.toString(), no_content.text.toString())
                actionOnNote(addNote, this.noteI!!)
            } else {
                this.noteI =
                    NoteI(this.noteI!!.id, no_title.text.toString(), no_content.text.toString())
                actionOnNote(updateNote, this.noteI!!)
            }
        }
    }

    fun goBack(){
        super.onBackPressed()
    }

    override fun onBackPressed() {
        field = fieldsType()
        if (field != Constants.NOT_EMPTY) {
            if (myAlert == null) {
                myAlert = MyAlert(this, Constants.TYPE_DISCARD_CHANGES,
                    Constants.MSG_DISCARD_CHANGES + field)
            } else {
                myAlert!!.setType(Constants.TYPE_DISCARD_CHANGES)
                myAlert!!.setMessage(Constants.MSG_DISCARD_CHANGES + field)
                myAlert!!.setActionButtonNames(Constants.DISCARD, Constants.CANCEL)
            }
            myAlert!!.show()
        } else {
            goBack()
        }
    }

}
