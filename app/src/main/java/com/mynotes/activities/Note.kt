package com.mynotes.activities

import android.os.Bundle
import com.mynotes.R
import com.mynotes.stuffs.BaseActivity

class Note : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note)
    }
}
