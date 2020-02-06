package com.mynotes.activities

import android.os.Bundle
import com.mynotes.R
import com.mynotes.stuffs.BaseActivity
import com.mynotes.stuffs.Strs
import kotlinx.android.synthetic.main.change_password.*

class ChangePassword : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_password)

        cp_back.setOnClickListener{onBackPressed()}
        cp_change.setOnClickListener{
            if(cp_new.text.isEmpty() && cp_newagain.text.isEmpty()){
                showAlert(this, Strs.ENTER_ALL)
            } else if (cp_new.text.isEmpty()){
                showAlert(this, Strs.ENTER_NEW_PASSCODE)
            } else if (cp_newagain.text.isEmpty()){
                showAlert(this, Strs.REENTER_NEW_PASSCODE)
            } else if (cp_new.text != (cp_newagain.text)){


            }
        }

    }
}
