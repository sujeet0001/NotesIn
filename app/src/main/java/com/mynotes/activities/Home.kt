package com.mynotes.activities

import android.content.Intent
import android.os.Bundle
import com.mynotes.R
import com.mynotes.stuffs.BaseActivity

class Home : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        startActivity(Intent(this, ChangePassword::class.java))
    }
}
