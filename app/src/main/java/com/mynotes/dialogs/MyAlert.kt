package com.mynotes.dialogs

import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.View
import com.mynotes.R
import com.mynotes.utils.Constants
import com.mynotes.utils.MyDialog
import kotlinx.android.synthetic.main.my_alert.*

class MyAlert(context: Context, type: Int, msg: String) : MyDialog(context) {

    private var type: Int = -1
    private var msg: String = ""
    private var ctx: Context = context

    init {
        this.type = type
        this.msg = msg
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDialog(R.drawable.rc_gray, closeOnBackPress = false, closeOnOutsideTouch = false)
        setContentView(R.layout.my_alert)
        ma_msg.text = msg
        when (type){
            Constants.FIRST_TIME -> {
                ma_close.visibility = View.VISIBLE
                ma_yes_no.visibility = View.GONE

                val str = SpannableString(msg)
                str.setSpan(RelativeSizeSpan(1.8f), msg.indexOf("0"),
                    msg.indexOf("1") + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                ma_msg.text = str
            }
            Constants.FIRST_TIME_SECRET_CODE_SETTING_INFO -> {
                ma_no.visibility = View.GONE
            }
        }

        setClickListeners()

    }

    private fun setClickListeners(){
        ma_yes.setOnClickListener{

            when (type){



                else -> dismiss()
            }

        }

        ma_no.setOnClickListener { dismiss() }
        ma_close.setOnClickListener{ dismiss() }
    }

}