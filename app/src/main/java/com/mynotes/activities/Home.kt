package com.mynotes.activities

import android.content.Intent
import android.os.Bundle
import com.mynotes.R
import com.mynotes.stuffs.BaseActivity
import kotlinx.android.synthetic.main.home.*

class Home : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        ho_add.setOnClickListener{}
        ho_chngpasscode.setOnClickListener{startActivity(Intent(applicationContext, ChangePassword::class.java))}
    }
}
