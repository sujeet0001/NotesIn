package com.mynotes.utils

import android.app.Dialog
import android.view.WindowManager

open class DialogViewCreator(private val dialog: Dialog) {

    private val window = dialog.window

    fun setBackground(res: Int): DialogViewCreator {
        this.window.setBackgroundDrawableResource(res)
        return this
    }

    fun setLayout(width: Int, height: Int): DialogViewCreator {
        this.window.setLayout(width, height)
        return this
    }

    fun setGravity(gravity: Int): DialogViewCreator {
        this.window.setGravity(gravity)
        return this
    }

    fun setDimAmt(dimAmt: Float): DialogViewCreator {
        this.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        this.window.setDimAmount(dimAmt)
        return this
    }

    fun setAnim(anim: Int): DialogViewCreator {
        this.window.attributes.windowAnimations = anim
        return this
    }

    fun closeOnBackPress(bool: Boolean): DialogViewCreator{
        this.dialog.setCancelable(bool)
        return this
    }

    fun closeOnOutsideTouch(bool: Boolean): DialogViewCreator{
        this.dialog.setCanceledOnTouchOutside(bool)
        return this
    }

}