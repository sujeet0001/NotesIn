package com.mynotes.stuffs

import android.content.res.Configuration
import android.view.View
import android.view.ViewGroup

import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginBottom
import com.google.android.material.snackbar.Snackbar
import com.mynotes.R

open class BaseActivity : AppCompatActivity() {

    fun adjustFontScale(configuration: Configuration) {
        configuration.fontScale = 1.0.toFloat()
        val metrics = resources.displayMetrics
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        baseContext.createConfigurationContext(configuration)
    }

    fun showAlert(msg: String) {
        val snack = Snackbar.make(window.decorView.rootView, msg, Snackbar.LENGTH_LONG)

       /*snack.apply {
            (this.view.layoutParams as ViewGroup.MarginLayoutParams)
                .apply { setMargins(50, 0, 50, 130) }
           show()
        }*/

        val view = snack.view
        view.setPadding(15, 15, 15, 15)
        view.setBackgroundResource(R.drawable.rc_white70)
        val tv = view.findViewById(R.id.snackbar_text) as TextView
        tv.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
        tv.textSize = 16f
        tv.textAlignment = View.TEXT_ALIGNMENT_CENTER
        tv.typeface = ResourcesCompat.getFont(applicationContext, R.font.bold)

        val params = view.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(12, 12, 12, 12)
        view.layoutParams = params

        snack.show()
    }
}