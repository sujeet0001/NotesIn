package com.mynotes.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import com.google.android.material.snackbar.Snackbar
import com.mynotes.R
import com.mynotes.stuffs.BaseActivity
import com.mynotes.stuffs.Prefs
import kotlinx.android.synthetic.main.passcode.*


class PassCode : BaseActivity() {

    private var code: String = ""
    private lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adjustFontScale(resources.configuration)
        setContentView(R.layout.passcode)

        pc_passcode.isEnabled = false
        prefs = Prefs(applicationContext)

        val dot: String = getString(R.string.dot)
        pc_0.setOnClickListener { addChar(dot, "0") }
        pc_1.setOnClickListener { addChar(dot, "1") }
        pc_2.setOnClickListener { addChar(dot, "2") }
        pc_3.setOnClickListener { addChar(dot, "3") }
        pc_4.setOnClickListener { addChar(dot, "4") }
        pc_5.setOnClickListener { addChar(dot, "5") }
        pc_6.setOnClickListener { addChar(dot, "6") }
        pc_7.setOnClickListener { addChar(dot, "7") }
        pc_8.setOnClickListener { addChar(dot, "8") }
        pc_9.setOnClickListener { addChar(dot, "9") }

        pc_back.setOnClickListener {
            if (pc_passcode.text.isNotEmpty()) {
                pc_passcode.text = pc_passcode.text.dropLast(1) as Editable?
                code = code.dropLast(1)
            }
        }
    }

    private fun addChar(s: String, ch: String) {
        pc_passcode.text.append(s)
        code += ch
        if(code == prefs.getPasscode()){
            startActivity(Intent(this, Home::class.java))
        }
    }
}
