package com.mynotes.utils

import android.content.res.Configuration
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import com.mynotes.R

open class BaseActivity: AppCompatActivity() {

    fun adjustFontScale(configuration: Configuration) {
        configuration.fontScale = 1.0.toFloat()
        val metrics = resources.displayMetrics
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        baseContext.createConfigurationContext(configuration)
    }

    fun showAlert(msg: String, time: Int) {
        val snack = Snackbar.make(window.decorView.rootView, msg, time)
        snack.apply {
            val view = snack.view
            view.setPadding(resources.getDimension(R.dimen.d10).toInt(),
                resources.getDimension(R.dimen.d10).toInt(),
                resources.getDimension(R.dimen.d10).toInt(),
                resources.getDimension(R.dimen.d10).toInt())
            view.setBackgroundResource(R.drawable.rc_gray)
            val tv = view.findViewById(R.id.snackbar_text) as TextView
            tv.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
            tv.textSize = resources.getDimension(R.dimen.t9)
            tv.typeface = ResourcesCompat.getFont(applicationContext, R.font.bold)
            tv.textAlignment = View.TEXT_ALIGNMENT_CENTER
            (this.view.layoutParams as ViewGroup.MarginLayoutParams)
                .apply { setMargins(resources.getDimension(R.dimen.d20).toInt(),
                    0,
                    resources.getDimension(R.dimen.d20).toInt(),
                    resources.getDimension(R.dimen.d50).toInt())
                }
            show()
        }
    }
}