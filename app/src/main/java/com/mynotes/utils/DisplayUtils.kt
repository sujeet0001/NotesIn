package com.mynotes.utils

import android.content.Context
import android.content.res.Configuration
import com.mynotes.R

class DisplayUtils {

    companion object {

        fun getTextColor(context: Context): Int {
            return if (Prefs.get(context).isDarkMode()) {
                R.color.black
            } else {
                R.color.grey
            }
        }

        fun getButtonTextColor(context: Context): Int {
            return if (Prefs.get(context).isDarkMode()) {
                R.color.black
            } else {
                R.color.white
            }
        }

        fun isSystemSetToDarkMode(context: Context): Boolean {
            return context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        }
    }
}