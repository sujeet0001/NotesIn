package com.mynotes.activities

import android.os.Bundle
import com.mynotes.R
import com.mynotes.stuffs.BaseActivity
import kotlinx.android.synthetic.main.note.*

class Note : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note)

        no_discard.setOnClickListener { onBackPressed() }
        no_save.setOnClickListener {  }


    }
}
