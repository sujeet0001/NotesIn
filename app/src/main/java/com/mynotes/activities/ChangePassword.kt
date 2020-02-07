package com.mynotes.activities

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import com.mynotes.R
import com.mynotes.stuffs.BaseActivity
import com.mynotes.stuffs.Prefs
import com.mynotes.stuffs.Strs
import kotlinx.android.synthetic.main.change_password.*

class ChangePassword : BaseActivity() {

    private var prefs: Prefs? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_password)

        cp_back.setOnClickListener { onBackPressed() }

        cp_change.setOnClickListener {
            if (cp_new.text.isEmpty() && cp_newagain.text.isEmpty()) {
                showAlert(Strs.ENTER_ALL)
            } else if (cp_new.text.isEmpty()) {
                showAlert(Strs.ENTER_NEW_PASSCODE)
            } else if (cp_newagain.text.isEmpty()) {
                showAlert(Strs.REENTER_NEW_PASSCODE)
            } else if (cp_new.text.toString() != cp_newagain.text.toString()) {
                showAlert(Strs.PASSCODES_MISMATCH)
            } else {
                prefs?.setPasscode(cp_new.text.toString())
                showAlert(Strs.PASSCODE_CHANGED)
                Handler().postDelayed({ onBackPressed() }, 1500)
            }
        }

        cp_old.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (prefs == null) {
                        prefs = Prefs(applicationContext)
                    }
                    if (s.toString() == prefs!!.getPasscode()) {
                        cp_old.visibility = View.GONE
                        cp_old.startAnimation(AnimationUtils.loadAnimation( applicationContext, R.anim.out_top))
                        cp_newpasscode_lay.visibility = View.VISIBLE
                        cp_newpasscode_lay.startAnimation(AnimationUtils.loadAnimation( applicationContext, R.anim.in_bottom))
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
    }
}
