package com.notesin.utils

import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import com.notesin.R

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

    fun getSnack(msg: String, time: Int, hasButton: Boolean): Snackbar {
        val snack = Snackbar.make(window.decorView.rootView, msg, time)
        val view = snack.view
        snack.apply {
            view.setPadding(resources.getDimension(R.dimen.d10).toInt(),
                resources.getDimension(R.dimen.d10).toInt(),
                if (!hasButton) {
                    resources.getDimension(R.dimen.d10).toInt()
                } else {
                    resources.getDimension(R.dimen.d15).toInt()
                },
                resources.getDimension(R.dimen.d10).toInt())
            view.setBackgroundResource(R.drawable.rc_grey)
            val tv = view.findViewById(R.id.snackbar_text) as TextView
            tv.textSize = DisplayUtils.getToastTextSize(applicationContext)
            tv.typeface = ResourcesCompat.getFont(applicationContext, R.font.regular)
            if (!hasButton) {
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
            if(hasButton){
                val btn = view.findViewById(R.id.snackbar_action) as Button
                btn.setBackgroundResource(R.drawable.rc_green_l)
                btn.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
                btn.textSize = DisplayUtils.getToastTextSize(applicationContext)
                btn.typeface = ResourcesCompat.getFont(applicationContext, R.font.bold)
                btn.isAllCaps = false
                btn.setPadding(
                    resources.getDimension(R.dimen.d20).toInt(), 0,
                    resources.getDimension(R.dimen.d20).toInt(), 0
                )
            }
        }
        return snack
    }

    fun recreateActivityOnThemeChange(activity: BaseActivity) {
        if (DisplayUtils.getTheme(applicationContext) != currentTheme) {
            currentTheme = DisplayUtils.getTheme(applicationContext)
            activity.recreate()
        }
    }

    fun getUniqueId(): Int {
        return Prefs.get(applicationContext).getInt(Constants.PREF_UNIQUE_ID) + 1
    }

    fun storeUniqueId() {
        Prefs.get(applicationContext).setInt(Constants.PREF_UNIQUE_ID, getUniqueId())
    }

    fun setVisibilityWithAnimation(view: View, visibility: Int, anim: Int){
        view.visibility = visibility
        view.startAnimation(AnimationUtils.loadAnimation(applicationContext, anim))
    }

    fun hideKeyboard(){
        val vw = this.currentFocus
        if (vw != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(vw.windowToken, 0)
        }
    }
}