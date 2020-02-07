package com.mynotes.stuffs

import android.content.res.Configuration
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

open class BaseActivity: AppCompatActivity(){

    fun adjustFontScale(configuration: Configuration) {
        configuration.fontScale = 1.0.toFloat()
        val metrics = resources.displayMetrics
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        baseContext.createConfigurationContext(configuration)
    }

    fun showAlert(msg: String){
        Snackbar.make(window.decorView.rootView, msg, Snackbar.LENGTH_LONG)
            .apply {
                (this.view.layoutParams as ViewGroup.MarginLayoutParams)
                    .apply { setMargins(50, 0, 50, 130) }
                show()
            }
    }
}