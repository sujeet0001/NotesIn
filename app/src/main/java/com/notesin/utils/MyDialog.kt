package com.notesin.utils

import android.app.Dialog
import android.content.Context
import android.view.WindowManager

open class MyDialog(context: Context) : Dialog(context) {

    fun setBackground(res: Int){
        window?.setBackgroundDrawableResource(res)
    }

    fun setLayout(width: Int, height: Int){
        window?.setLayout(width, height)
    }

    fun setGravity(gravity: Int){
        window?.setGravity(gravity)
    }

    fun setDimAmt(dimAmt: Float){
        window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        window?.setDimAmount(dimAmt)
    }

    fun setAnim(anim: Int){
        window?.attributes?.windowAnimations = anim
    }

    fun dialogDismissOn(onBackPress: Boolean, onOutsideTouch: Boolean){
        setCancelable(onBackPress)
        setCanceledOnTouchOutside(onOutsideTouch)
    }

    fun setUpDialog(background: Int, closeOnBackPress: Boolean, closeOnOutsideTouch: Boolean){
        setBackground(background)
        dialogDismissOn(closeOnBackPress, closeOnOutsideTouch)
    }

}