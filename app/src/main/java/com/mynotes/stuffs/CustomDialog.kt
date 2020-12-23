package com.mynotes.utils

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.view.WindowManager

open class CustomDialog(context: Context) : Dialog(context) {

    // always call after before setting ContentView
    fun setUpDialog(window: Window?, background: Int?, gravity: Int?, width: Int?, height: Int?,
                    dim: Boolean?, closeOnBackPress: Boolean?, closeOnOutsideTouch: Boolean?,
                    anim: Int?){
        window?.let {
            anim?.let { window.attributes.windowAnimations = anim }
            gravity?.let { window.setGravity(it) }
            background?.let { window.setBackgroundDrawableResource(it) }

            window.setLayout(width ?: WindowManager.LayoutParams.WRAP_CONTENT,
                height ?: WindowManager.LayoutParams.WRAP_CONTENT)

            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            dim?.let {
                if (!it) {
                    window.setDimAmount(0f)
                } else {
                    window.setDimAmount(0.9f)
                }
            }
            closeOnBackPress?.let { setCancelable(it) }
            closeOnOutsideTouch?.let { setCanceledOnTouchOutside(it) }
        }
    }
}