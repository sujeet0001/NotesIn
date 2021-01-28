package com.notesin.dialogs

import android.content.Context
import android.os.Bundle
import com.notesin.R
import com.notesin.utils.MyDialog

class ProgressCircle(context: Context): MyDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDialog(R.color.trans, closeOnBackPress = false, closeOnOutsideTouch = false)
        setDimAmt(0f)
        setContentView(R.layout.progress_circle)
    }

}