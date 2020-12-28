package com.mynotes.utils

import android.content.res.Configuration
import android.os.Handler
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

    var currentTheme: Int = 0

    fun setViewConfigs(configuration: Configuration, currentTheme: Int){
        this.currentTheme = currentTheme
        setTheme(currentTheme)
        adjustFontScale(configuration)
    }

    private fun adjustFontScale(configuration: Configuration) {
        configuration.fontScale = 1.0.toFloat()
        val metrics = resources.displayMetrics
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        baseContext.createConfigurationContext(configuration)
    }

    fun showSnack(msg: String, time: Int) {
        val snack = Snackbar.make(window.decorView.rootView, msg, time)
        snack.apply {
            val view = snack.view
            view.setPadding(resources.getDimension(R.dimen.d10).toInt(),
                resources.getDimension(R.dimen.d10).toInt(),
                resources.getDimension(R.dimen.d10).toInt(),
                resources.getDimension(R.dimen.d10).toInt())
            view.setBackgroundResource(R.drawable.rc_grey)
            val tv = view.findViewById(R.id.snackbar_text) as TextView
            tv.textSize = resources.getDimension(R.dimen.t9)
            tv.typeface = ResourcesCompat.getFont(applicationContext, R.font.regular)
            tv.textAlignment = View.TEXT_ALIGNMENT_CENTER
            tv.maxLines = 10
            tv.setTextColor(ContextCompat.getColor(applicationContext, DisplayUtils.getToastTextColor(applicationContext)))
            (this.view.layoutParams as ViewGroup.MarginLayoutParams)
                .apply { setMargins(resources.getDimension(R.dimen.d20).toInt(),
                    0,
                    resources.getDimension(R.dimen.d20).toInt(),
                    resources.getDimension(R.dimen.d70).toInt())
                }
            show()
        }
    }

    fun recreateActivityOnThemeChange(activity: BaseActivity){
        if(DisplayUtils.getTheme(applicationContext) != currentTheme){
            currentTheme = DisplayUtils.getTheme(applicationContext)
            Handler().postDelayed({
                activity.recreate()
            }, 50)
        }
    }
}