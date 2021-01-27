package com.mynotes.dialogs

import android.content.Context
import android.os.Bundle
import com.mynotes.R
import com.mynotes.utils.MyDialog

class ProgressCircle(context: Context): MyDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDialog(R.color.trans, closeOnBackPress = false, closeOnOutsideTouch = false)
        setContentView(R.layout.progress_circle)
    }

}