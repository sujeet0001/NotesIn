package com.mynotes.utils

import android.content.Context
import android.content.res.Configuration
import com.mynotes.R

class DisplayUtils {

    companion object {

        fun getToastTextColor(context: Context): Int {
            return when(getTheme(context)){
                R.style.AppThemeDark -> {
                    R.color.black
                } else -> {
                    R.color.grey
                }
            }
        }

        fun isSystemSetToDarkMode(context: Context): Boolean {
            return context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        }

        fun getTheme(context: Context): Int {
            return if (Prefs.get(context).isSystemThemeChangesAppTheme()) {
                if (isSystemSetToDarkMode(context)) {
                    R.style.AppThemeDark
                } else {
                    R.style.AppThemeLight
                }
            } else {
                if (Prefs.get(context).isDarkMode()) {
                    R.style.AppThemeDark
                } else {
                    R.style.AppThemeLight
                }
            }
        }
    }
}