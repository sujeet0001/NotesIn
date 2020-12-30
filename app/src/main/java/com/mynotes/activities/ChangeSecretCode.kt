package com.mynotes.activities

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import com.mynotes.R
import com.mynotes.dialogs.MyAlert
import com.mynotes.utils.BaseActivity
import com.mynotes.utils.Constants
import com.mynotes.utils.Prefs
import com.mynotes.utils.DisplayUtils
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
        setViewConfigs(resources.configuration)
        setContentView(R.layout.change_secret_code)
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
                    cp_old.visibility = View.GONE
                    cp_old.startAnimation(
                        AnimationUtils.loadAnimation(applicationContext, R.anim.out_top)
                    )
                    cp_newpasscode_lay.visibility = View.VISIBLE
                    cp_newpasscode_lay.startAnimation(
                        AnimationUtils.loadAnimation(applicationContext, R.anim.in_bottom)
                    )
                    cp_new.eb_et.hint = resources.getText(R.string.enter_new_secret_code)
                    cp_newagain.eb_et.hint = resources.getText(R.string.reenter_new_secret_code)
                    highlightText(cp_new, cp_newagain, cp_new.eb_et)
                    code = ""
                    msg = "Type in same codes in both the fields and make sure the code is of minimum 4 characters"
                } else {
                    msg = if (Prefs.get(applicationContext).isSecretCodeEnabled()) {
                        Prefs.get(applicationContext).enableSecretCode(false)
                        "From now on you wont be asked to enter secret code while opening the app"
                    } else {
                        Prefs.get(applicationContext).enableSecretCode(true)
                        "From now on you will be asked to enter secret code while opening the app"
                    }
                    Handler().postDelayed({ finish() }, 4000)
                }
                showSnack(msg, 4000)
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
                showSnack(Constants.SAME_AS_EXISTING_CODE, 3000)
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
                    showSnack(Constants.SAME_AS_EXISTING_CODE, 3000)
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
        snack = Snackbar.make(window.decorView.rootView, "Codes matched, want to save it?", 5000)
        snack?.apply {
            val view = snack?.view
            view?.setPadding(
                resources.getDimension(R.dimen.d10).toInt(),
                resources.getDimension(R.dimen.d10).toInt(),
                resources.getDimension(R.dimen.d15).toInt(),
                resources.getDimension(R.dimen.d10).toInt()
            )
            view?.setBackgroundResource(R.drawable.rc_grey)
            val tv = view?.findViewById(R.id.snackbar_text) as TextView
            tv.textSize = resources.getDimension(R.dimen.t9)
            tv.maxLines = 3
            tv.typeface = ResourcesCompat.getFont(applicationContext, R.font.regular)
            tv.setTextColor(ContextCompat.getColor(applicationContext,
                    DisplayUtils.getToastTextColor(applicationContext)))
            snack?.setAction("Yes") {
                Prefs.get(applicationContext).setSecretCode(codeNew)
                showSnack(Constants.SECRET_CODE_CHANGED, Snackbar.LENGTH_LONG)
                Prefs.get(applicationContext).setBool(Constants.PREF_SECRET_CODE_SET, true)
                Handler().postDelayed({
                    finish()
                }, 1500)
            }
            val btn = view.findViewById(R.id.snackbar_action) as Button
            btn.setBackgroundResource(R.drawable.rc_x_green)
            btn.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
            btn.textSize = resources.getDimension(R.dimen.t9)
            btn.typeface = ResourcesCompat.getFont(applicationContext, R.font.bold)
            btn.isAllCaps = false
            btn.setPadding(
                resources.getDimension(R.dimen.d20).toInt(), 0,
                resources.getDimension(R.dimen.d20).toInt(), 0
            )
            (this.view.layoutParams as ViewGroup.MarginLayoutParams)
                .apply {
                    setMargins(
                        resources.getDimension(R.dimen.d20).toInt(),
                        0,
                        resources.getDimension(R.dimen.d20).toInt(),
                        resources.getDimension(R.dimen.d70).toInt()
                    )
                }
            show()
        }
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