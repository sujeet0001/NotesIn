package com.notesin.activities

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.notesin.R
import com.notesin.dialogs.MyAlert
import com.notesin.utils.BaseActivity
import com.notesin.utils.Constants
import com.notesin.utils.Prefs
import kotlinx.android.synthetic.main.change_secret_code.*
import kotlinx.android.synthetic.main.edittext_backspace.view.*

class ChangeSecretCode : BaseActivity() {

    private var code: String = ""
    private var codeNew: String = ""
    private var dot: String = ""
    private var tv: TextView? = null
    private var snack: Snackbar? = null
    private var fromChangeCode: Boolean = false

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        recreateActivityOnThemeChange(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewConfigs(resources.configuration, R.layout.change_secret_code)
        setViews()
        setClickListeners()
    }

    private fun setViews() {

        fromChangeCode = intent.getBooleanExtra(Constants.FROM_CHANGE_SECRET_CODE, false)
        if (fromChangeCode) {
            if (Prefs.get(applicationContext).getBool(Constants.PREF_SECRET_CODE_SET)) {
                cp_title.text = Constants.CHANGE_SECRET_CODE
            } else {
                cp_title.text = Constants.SET_SECRET_CODE
                MyAlert(this, Constants.TYPE_SET_SECRET_CODE,
                    Constants.MSG_SET_SECRET_CODE
                ).show()

            }
        } else {
            if (Prefs.get(applicationContext).isSecretCodeEnabled()) {
                cp_title.text = Constants.DISABLE_SECRET_CODE
            } else {
                cp_title.text = Constants.ENABLE_SECRET_CODE
            }
        }

        dot = getString(R.string.dot)
        tv = cp_old.eb_et
        cp_old.setBackgroundResource(R.drawable.view_selector)
    }

    private fun setClickListeners() {
        cp_back.setOnClickListener { onBackPressed() }
        cp_0.setOnClickListener { addChar("0") }
        cp_1.setOnClickListener { addChar("1") }
        cp_2.setOnClickListener { addChar("2") }
        cp_3.setOnClickListener { addChar("3") }
        cp_4.setOnClickListener { addChar("4") }
        cp_5.setOnClickListener { addChar("5") }
        cp_6.setOnClickListener { addChar("6") }
        cp_7.setOnClickListener { addChar("7") }
        cp_8.setOnClickListener { addChar("8") }
        cp_9.setOnClickListener { addChar("9") }
        cp_new.setOnClickListener { highlightText(it, cp_newagain, cp_new.eb_et) }
        cp_newagain.setOnClickListener { highlightText(it, cp_new, cp_newagain.eb_et) }
        cp_old.eb_backspace.setOnClickListener { removeChar(cp_old.eb_et) }
        cp_new.eb_backspace.setOnClickListener { removeChar(cp_new.eb_et) }
        cp_newagain.eb_backspace.setOnClickListener { removeChar(cp_newagain.eb_et) }
    }

    private fun addChar(ch: String) {
        dismissSnack()
        tv?.append(dot)
        if (tv == cp_old.eb_et) {
            code += ch
            if (code == Prefs.get(applicationContext).getSecretCode()) {
                val msg: String
                if (fromChangeCode) {
                    setVisibilityWithAnimation(cp_old, View.GONE, R.anim.out_top)
                    setVisibilityWithAnimation(cp_newpasscode_lay, View.VISIBLE, R.anim.in_bottom)
                    cp_new.eb_et.hint = resources.getText(R.string.enter_new_secret_code)
                    cp_newagain.eb_et.hint = resources.getText(R.string.reenter_new_secret_code)
                    highlightText(cp_new, cp_newagain, cp_new.eb_et)
                    code = ""
                    msg = "Type in same codes in both the fields and make sure the code is of minimum 4 characters"
                } else {
                    msg = if (Prefs.get(applicationContext).isSecretCodeEnabled()) {
                        Prefs.get(applicationContext).enableSecretCode(false)
                        "From now on you won't be asked to enter secret code while opening the app"
                    } else {
                        Prefs.get(applicationContext).enableSecretCode(true)
                        "From now on you will be asked to enter secret code while opening the app"
                    }
                    Handler().postDelayed({ finish() }, 3500)
                }
                getSnack(msg, 4000, false).show()
            }
        } else if (tv == cp_new.eb_et) {
            code += ch
        } else {
            codeNew += ch
        }

        if (code == codeNew && code.length > 3) {
            if (code != Prefs.get(applicationContext).getSecretCode()) {
                showSecretCodeSaveAlert()
            } else {
                getSnack(Constants.SAME_AS_EXISTING_CODE, 3000, false).show()
            }
        }
    }

    private fun removeChar(tv: TextView) {
        dismissSnack()
        if (tv.text.isNotEmpty()) {
            tv.text = tv.text.dropLast(1)
            when (tv) {
                cp_old.eb_et -> {
                    code = code.dropLast(1)
                }
                cp_new.eb_et -> {
                    code = code.dropLast(1)
                }
                else -> {
                    codeNew = codeNew.dropLast(1)
                }
            }
            if (code == codeNew && code.length > 3) {
                if (code == Prefs.get(applicationContext).getSecretCode()) {
                    getSnack(Constants.SAME_AS_EXISTING_CODE, 3000, false).show()
                } else {
                    showSecretCodeSaveAlert()
                }
            }
        }
    }

    private fun highlightText(v1: View, v2: View, tv: TextView) {
        dismissSnack()
        v1.setBackgroundResource(R.drawable.view_selector)
        v2.setBackgroundResource(R.drawable.rcs_white70)
        this.tv = tv
    }

    private fun showSecretCodeSaveAlert() {
        snack = getSnack("Codes matched, want to save it?", 5000, true)
        snack?.setAction("Yes") {
            Prefs.get(applicationContext).setSecretCode(codeNew)
            getSnack(Constants.SECRET_CODE_CHANGED, Snackbar.LENGTH_LONG, false).show()
            Prefs.get(applicationContext).setBool(Constants.PREF_SECRET_CODE_SET, true)
            Handler().postDelayed({
                finish()
            }, 1500)
        }?.show()
    }

    private fun dismissSnack() {
        if (snack != null) {
            snack?.dismiss()
            snack = null
        }
    }

    override fun onRestart() {
        super.onRestart()
        recreateActivityOnThemeChange(this)
    }
}