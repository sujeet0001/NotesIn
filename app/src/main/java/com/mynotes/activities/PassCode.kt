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
    private var dot: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adjustFontScale(resources.configuration)
        setContentView(R.layout.passcode)

        prefs = Prefs(applicationContext)
        dot = getString(R.string.dot)

        pc_0.setOnClickListener { addChar("0") }
        pc_1.setOnClickListener { addChar("1") }
        pc_2.setOnClickListener { addChar("2") }
        pc_3.setOnClickListener { addChar("3") }
        pc_4.setOnClickListener { addChar("4") }
        pc_5.setOnClickListener { addChar("5") }
        pc_6.setOnClickListener { addChar("6") }
        pc_7.setOnClickListener { addChar("7") }
        pc_8.setOnClickListener { addChar("8") }
        pc_9.setOnClickListener { addChar("9") }

        pc_back.setOnClickListener {
            if (pc_passcode.text.isNotEmpty()) {
                pc_passcode.text = pc_passcode.text.dropLast(1) as Editable?
                code = code.dropLast(1)
            }
        }
    }

    private fun addChar(ch: String) {
        pc_passcode.text.append(dot)
        code += ch
        if(code == prefs.getPasscode()){
            startActivity(Intent(this, Home::class.java))
        }
    }
}
