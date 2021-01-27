package com.mynotes.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mynotes.R
import com.mynotes.adapters.NotesA
import com.mynotes.dialogs.ProgressCircle
import com.mynotes.models.NoteI
import com.mynotes.utils.BaseActivity
import com.mynotes.utils.Constants
import com.mynotes.utils.NotesDB
import kotlinx.android.synthetic.main.home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class Home : BaseActivity() {

    private var notes = ArrayList<NoteI>()
    private var scope: CoroutineScope? = null
    private var progressCircle: ProgressCircle? = null
    private lateinit var notesAdapter: NotesA
    private val reqCode = 1

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        recreateActivityOnThemeChange(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewConfigs(resources.configuration, R.layout.home)

        ho_settings.setOnClickListener{startActivity(Intent(applicationContext, Settings::class.java))}
        ho_add.setOnClickListener{ goToNote(null) }

        setListAdapter()
        populateList()
    }

    private fun setListAdapter(){
        ho_list.setHasFixedSize(true)
        ho_list.layoutManager = (LinearLayoutManager(applicationContext))
        notesAdapter = NotesA(this, notes)
        ho_list.adapter = notesAdapter
    }

    private fun checkForNotesAvailability(){
        if(notes.isEmpty()){
            ho_no_notes.visibility = View.VISIBLE
        } else {
            ho_no_notes.visibility = View.GONE
        }
    }

    override fun onRestart() {
        super.onRestart()
        recreateActivityOnThemeChange(this)
    }

    fun goToNote(noteI: NoteI?){
        val intent = Intent(this, Note::class.java)
        if(noteI == null){
            intent.putExtra(Constants.FROM_HOME, true)
        } else {
            intent.putExtra("Note", noteI)
        }
        startActivityForResult(intent, reqCode)
    }

    private fun populateList(){
        notes.clear()
        if(progressCircle == null){
            progressCircle = ProgressCircle(this)
        }
        if(scope == null){
            scope = CoroutineScope(Dispatchers.Main)
        }
        scope?.launch {
            notes.addAll(NotesDB.get(applicationContext)?.notesDao()?.getAllNotes() as ArrayList)
            notes.reverse()
            if(progressCircle != null && progressCircle?.isShowing == true){
                progressCircle?.dismiss()
            }
            notesAdapter.notifyDataSetChanged()
            checkForNotesAvailability()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(scope != null){
            scope?.cancel()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == reqCode){
            populateList()
        }
    }
}
