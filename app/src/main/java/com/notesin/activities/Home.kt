package com.notesin.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.notesin.R
import com.notesin.adapters.NotesA
import com.notesin.dialogs.ProgressCircle
import com.notesin.models.NoteI
import com.notesin.utils.BaseActivity
import com.notesin.utils.Constants
import com.notesin.utils.NotesDB
import kotlinx.android.synthetic.main.home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class Home : BaseActivity() {

    private var notes = ArrayList<NoteI>()
    private var filteredNotes: ArrayList<NoteI>? = null
    private var scope: CoroutineScope? = null
    private var progressCircle: ProgressCircle? = null
    private lateinit var notesAdapter: NotesA

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        recreateActivityOnThemeChange(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewConfigs(resources.configuration, R.layout.home)
        setListeners()
        setLayoutManager()
        setListAdapter(notes)
        populateList()
    }

    private fun setListeners(){
        ho_settings.setOnClickListener{startActivity(Intent(applicationContext, Settings::class.java))}
        ho_add.setOnClickListener{ goToNote(null) }
        ho_search.setOnClickListener {
            ho_header.visibility = View.GONE
            animateView(ho_header, R.anim.out_top)
            ho_search_lay.visibility = View.VISIBLE
            animateView(ho_search_lay, R.anim.in_bottom)
        }
        ho_cancel_search.setOnClickListener {
            hideKeyboard()
            if(ho_search_bar.text.isNotEmpty()){
                ho_search_bar.text.clear()
            }
            ho_search_lay.visibility = View.GONE
            animateView(ho_search_lay, R.anim.out_bottom)
            ho_header.visibility = View.VISIBLE
            animateView(ho_header, R.anim.in_top)
        }
        ho_clear_search.setOnClickListener {
            if(ho_search_bar.text.isNotEmpty()){
                ho_search_bar.text.clear()
            }
        }
        ho_search_bar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s?.trim()?.isNotEmpty()!!) {
                    if(filteredNotes == null){
                        filteredNotes = ArrayList()
                    }
                    filteredNotes!!.clear()
                    for (it in notes) {
                        if(it.title.toLowerCase(Locale.ROOT).contains(s.trim().toString().toLowerCase(
                                Locale.ROOT))) {
                            filteredNotes!!.add(it)
                        }
                    }
                    setListAdapter(filteredNotes!!)
                } else {
                    setListAdapter(notes)
                }
            }
        })
    }

    private fun setLayoutManager(){
        ho_list.setHasFixedSize(true)
        ho_list.layoutManager = (LinearLayoutManager(applicationContext))
    }

    private fun setListAdapter(list: ArrayList<NoteI>){
        notesAdapter = NotesA(this, list)
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
        populateList()
    }

    fun goToNote(noteI: NoteI?){
        val intent = Intent(this, Note::class.java)
        if(noteI == null){
            intent.putExtra(Constants.FROM_ADD_NOTE, true)
        } else {
            intent.putExtra("Note", noteI)
        }
        startActivity(intent)
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
}
