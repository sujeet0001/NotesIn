package com.mynotes.utils

import android.content.res.Configuration
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import com.mynotes.R

open class BaseActivity : AppCompatActivity() {

    var currentTheme: Int = 0

    fun setViewConfigs(configuration: Configuration, layoutId: Int) {
        this.currentTheme = DisplayUtils.getTheme(applicationContext)
        setTheme(this.currentTheme)
        adjustFontScale(configuration)
        setContentView(layoutId)
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
            setSnackView(snack.view, false)
            show()
        }
    }

    fun getSnackWithButton(msg: String, time: Int): Snackbar {
        val snack = Snackbar.make(window.decorView.rootView, msg, time)
        snack.apply {
            setSnackView(snack.view, true)
            setSnackButtonView(snack.view, DisplayUtils.getToastTextSize(applicationContext))
        }
        return snack
    }

    private fun setSnackView(view: View, isButton: Boolean) {
        view.setPadding(resources.getDimension(R.dimen.d10).toInt(),
            resources.getDimension(R.dimen.d10).toInt(),
            if (!isButton) {
                resources.getDimension(R.dimen.d10).toInt()
            } else {
                resources.getDimension(R.dimen.d15).toInt()
            },
            resources.getDimension(R.dimen.d10).toInt())
        view.setBackgroundResource(R.drawable.rc_grey)
        val tv = view.findViewById(R.id.snackbar_text) as TextView
        tv.textSize = DisplayUtils.getToastTextSize(applicationContext)
        tv.typeface = ResourcesCompat.getFont(applicationContext, R.font.regular)
        if (!isButton) {
            tv.textAlignment = View.TEXT_ALIGNMENT_CENTER
        }
        tv.maxLines = 10
        tv.setTextColor(ContextCompat.getColor(applicationContext,
            DisplayUtils.getToastTextColor(applicationContext)))
        (view.layoutParams as ViewGroup.MarginLayoutParams)
            .apply {
                setMargins(resources.getDimension(R.dimen.d20).toInt(), 0,
                    resources.getDimension(R.dimen.d20).toInt(),
                    resources.getDimension(R.dimen.d70).toInt())
            }
    }

    private fun setSnackButtonView(view: View, textSize: Float) {
        val btn = view.findViewById(R.id.snackbar_action) as Button
        btn.setBackgroundResource(R.drawable.rc_x_green)
        btn.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
        btn.textSize = textSize
        btn.typeface = ResourcesCompat.getFont(applicationContext, R.font.bold)
        btn.isAllCaps = false
        btn.setPadding(
            resources.getDimension(R.dimen.d20).toInt(), 0,
            resources.getDimension(R.dimen.d20).toInt(), 0
        )
    }

    fun recreateActivityOnThemeChange(activity: BaseActivity) {
        if (DisplayUtils.getTheme(applicationContext) != currentTheme) {
            currentTheme = DisplayUtils.getTheme(applicationContext)
            activity.recreate()
        }
    }
}