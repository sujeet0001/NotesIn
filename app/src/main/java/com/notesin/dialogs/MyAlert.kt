package com.notesin.dialogs

import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import com.notesin.R
import com.notesin.activities.Note
import com.notesin.utils.Constants
import com.notesin.utils.MyDialog
import kotlinx.android.synthetic.main.first_time_alert.*
import kotlinx.android.synthetic.main.my_alert.*

class MyAlert(context: Context) : MyDialog(context) {

    private var ctx = context
    private var type = 0
    private var msg = ""

    constructor(context: Context, type: Int, msg: String): this(context){
        this.ctx = context
        this.type = type
        this.msg = msg
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDialog(R.color.trans, closeOnBackPress = false, closeOnOutsideTouch = false)

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

            when (type){
                Constants.TYPE_DISCARD_CHANGES -> {
                    ma_yes.text = Constants.DISCARD
                    ma_no.text = Constants.CANCEL
                }
            }

        }
    }

    fun setMessage(msg: String){
        ma_msg.text = msg
    }

    fun setType(type: Int){
        this.type = type
    }

    fun setActionButtonNames(positive: String, negative: String){
        ma_yes.text = positive
        ma_no.text = negative
    }

    private fun setClickListeners(){
        ma_yes.setOnClickListener {
            when (type){
                Constants.TYPE_DELETE_NOTE -> {
                    val note = ctx as Note
                    note.noteI?.let { it1 -> note.actionOnNote(2, it1) }
                }
                Constants.TYPE_DISCARD_CHANGES -> {
                    val note = ctx as Note
                    note.goBack()
                }
                else -> dismiss()
            }
            dismiss()
        }
        ma_no.setOnClickListener { dismiss() }
        ma_close.setOnClickListener{ dismiss() }
    }

}