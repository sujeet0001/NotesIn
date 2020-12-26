package com.mynotes.activities

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme()
        adjustFontScale(resources.configuration)
        setContentView(R.layout.change_secret_code)

        //if(!Prefs.get(this).getBool(Constants.PREF_FIRST_TIME_ON_SECRET_CODE_SETTING)){
            MyAlert(this, Constants.TYPE_FIRST_TIME_SECRET_CODE_SETTING,
                Constants.MSG_SET_SECRET_CODE).show()
            Prefs.get(this).setBool(Constants.PREF_FIRST_TIME_ON_SECRET_CODE_SETTING, true)
        //}

        dot = getString(R.string.dot)
        tv = cp_old.eb_et
        cp_old.setBackgroundResource(R.drawable.view_selector)
        setClickListeners()
    }

    private fun setClickListeners(){
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
                showSnack("Type in same codes in both the fields", 4000)
            }
        } else if (tv == cp_new.eb_et) {
            code += ch
        } else {
            codeNew += ch
        }

        if (tv != cp_old.eb_et && cp_new.eb_et.text.isNotEmpty() && (code == codeNew)) {
            showSecretCodeSaveAlert()
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
            if (this.tv != cp_old.eb_et && cp_new.eb_et.text.isNotEmpty() && (code == codeNew)) {
                showSecretCodeSaveAlert()
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
            tv.setTextColor(ContextCompat.getColor(applicationContext, DisplayUtils.getTextColor(applicationContext)))
            snack?.setAction("Yes") {
                Prefs.get(applicationContext).setSecretCode(codeNew)
                showSnack(Constants.SECRET_CODE_CHANGED, Snackbar.LENGTH_LONG)
                Handler().postDelayed({
                    onBackPressed()
                }, 2000)
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
                        resources.getDimension(R.dimen.d50).toInt()
                    )
                }
            show()
        }
    }

    private fun dismissSnack(){
        if(snack != null){
            snack?.dismiss()
            snack = null
        }
    }
}
