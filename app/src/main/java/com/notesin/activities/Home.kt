package com.notesin.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
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
            if(notes.isNotEmpty()){
                setVisibilityWithAnimation(ho_header, View.GONE, R.anim.out_top)
                setVisibilityWithAnimation(ho_add, View.GONE, R.anim.out_bottom)
                setVisibilityWithAnimation(ho_search_lay, View.VISIBLE, R.anim.in_bottom)
            } else {
                getSnack(Constants.NO_NOTES_TO_SEARCH, Snackbar.LENGTH_LONG, false).show()
            }
        }
        ho_cancel_search.setOnClickListener {
            hideKeyboard()
            if(ho_search_bar.text.isNotEmpty()){
                ho_search_bar.text.clear()
            }
            setVisibilityWithAnimation(ho_search_lay, View.GONE, R.anim.out_bottom)
            setVisibilityWithAnimation(ho_header, View.VISIBLE, R.anim.in_top)
            setVisibilityWithAnimation(ho_add, View.VISIBLE, R.anim.in_bottom)
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
                    checkForNotesAvailability(filteredNotes!!, true)
                } else {
                    setListAdapter(notes)
                    checkForNotesAvailability(notes, false)
                }
            }
        })
    }

    private fun setLayoutManager(){
        ho_list.setHasFixedSize(true)
        ho_list.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun setListAdapter(list: ArrayList<NoteI>){
        notesAdapter = NotesA(this, list)
        ho_list.adapter = notesAdapter
    }

    private fun checkForNotesAvailability(list: ArrayList<NoteI>, isFilteredList: Boolean){
        if(list.isEmpty()){
            ho_no_notes.visibility = View.VISIBLE
            if(isFilteredList){
                ho_no_notes.text = Constants.NO_SEARCH_RESULTS
            } else {
                ho_no_notes.text = Constants.NO_NOTES
            }
        } else {
            ho_no_notes.visibility = View.GONE
        }
    }

    override fun onRestart() {
        super.onRestart()
        recreateActivityOnThemeChange(this)
        if(ho_search_lay.visibility == View.VISIBLE){
            ho_search_lay.visibility = View.GONE
            ho_header.visibility = View.VISIBLE
            ho_add.visibility = View.VISIBLE
            if(ho_search_bar.text.isNotEmpty()){
                ho_search_bar.text.clear()
            }
        }
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
        progressCircle?.show()
        if(scope == null){
            scope = CoroutineScope(Dispatchers.Main)
        }
        scope?.launch {
            notes.addAll(NotesDB.get(applicationContext)?.notesDao()?.getAllNotes() as ArrayList)
            notes.reverse()
            notesAdapter.notifyDataSetChanged()
            checkForNotesAvailability(notes, false)
            if(progressCircle != null && progressCircle?.isShowing == true){
                progressCircle?.dismiss()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(scope != null){
            scope?.cancel()
        }
    }
}
