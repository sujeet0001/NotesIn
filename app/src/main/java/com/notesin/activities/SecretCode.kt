package com.notesin.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import com.notesin.R
import com.notesin.dialogs.MyAlert
import com.notesin.utils.BaseActivity
import com.notesin.utils.Constants
import com.notesin.utils.Prefs
import kotlinx.android.synthetic.main.secret_code.*

class SecretCode : BaseActivity() {

    private var code: String = ""
    private var dot: String = ""

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        recreateActivityOnThemeChange(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(!Prefs.get(applicationContext).isSecretCodeEnabled()){
            goToHome()
            finish()
            return
        }

        setViewConfigs(resources.configuration, R.layout.secret_code)

        if(!Prefs.get(applicationContext).getBool(Constants.PREF_FIRST_TIME_OPEN)){
            MyAlert(this, Constants.TYPE_FIRST_TIME, Constants.MSG_WELCOME).show()
            Prefs.get(applicationContext).setBool(Constants.PREF_FIRST_TIME_OPEN, true)
        }

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

        pc_help.setOnClickListener { getSnack("Enter your secret code to proceed. " +
                "If you have not set your secret code yet, you can type in '0001' " +
                "which is the default secret code to proceed.", 8000, false).show() }
    }

    private fun addChar(ch: String) {
        if(code.length < 15){
            pc_passcode.text.append(dot)
            code += ch
            if(code == Prefs.get(applicationContext).getSecretCode()){
                goToHome()
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        recreateActivityOnThemeChange(this)
    }

    private fun goToHome(){
        startActivity(Intent(this, Home::class.java))
    }
}
