package com.mynotes.dialogs

import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import com.mynotes.R
import com.mynotes.activities.Note
import com.mynotes.utils.Constants
import com.mynotes.utils.MyDialog
import kotlinx.android.synthetic.main.first_time_alert.*
import kotlinx.android.synthetic.main.my_alert.*

class MyAlert(context: Context, private var type: Int, private var msg: String) : MyDialog(context) {

    private val ctx: Context = context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDialog(R.drawable.rc_grey, closeOnBackPress = false, closeOnOutsideTouch = false)

        if(type == Constants.TYPE_FIRST_TIME || type == Constants.TYPE_SET_SECRET_CODE){
            setContentView(R.layout.first_time_alert)
            fta_close.setOnClickListener { dismiss() }
            val str = SpannableString(msg)
            if(type == Constants.TYPE_FIRST_TIME){
                str.setSpan(RelativeSizeSpan(1.7f), msg.indexOf("0"),
                    msg.indexOf("1") + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            fta_msg.text = str

        } else {
            setContentView(R.layout.my_alert)
            ma_msg.text = msg
            setClickListeners()
        }
    }

    private fun setClickListeners(){
        ma_yes.setOnClickListener {
            when (type){
                Constants.TYPE_DELETE_NOTE -> {
                    val note = ctx as Note
                    note.noteI?.let { it1 -> note.actionOnNote(2, it1) }
                }
                else -> dismiss()
            }
            dismiss()
        }
        ma_no.setOnClickListener { dismiss() }
        ma_close.setOnClickListener{ dismiss() }
    }

}