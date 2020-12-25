package com.mynotes.utils

import android.app.Dialog
import android.content.Context
import android.view.WindowManager

open class MyDialog(context: Context) : Dialog(context) {

    private val win = window

    fun setBackground(res: Int){
        this.win.setBackgroundDrawableResource(res)
    }

    fun setLayout(width: Int, height: Int){
        this.win.setLayout(width, height)
    }

    fun setGravity(gravity: Int){
        this.win.setGravity(gravity)
    }

    fun setDimAmt(dimAmt: Float){
        this.win.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        this.win.setDimAmount(dimAmt)
    }

    fun setAnim(anim: Int){
        this.win.attributes.windowAnimations = anim
    }

    fun dismissDialog(onBackPress: Boolean, onOutsideTouch: Boolean){
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

    fun setUpDialog(background: Int, closeOnBackPress: Boolean, closeOnOutsideTouch: Boolean){
        setBackground(background)
        dismissDialog(closeOnBackPress, closeOnOutsideTouch)
    }

}