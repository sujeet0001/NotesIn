package com.mynotes.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.mynotes.R

class ViewUtils() {

    companion object {

        fun getTextColor(context: Context): Int{
            return if(Prefs.get(context).getDarkMode()){
                R.color.black
            } else {
                R.color.grey
            }
        }

        fun getButtonTextColor(context: Context): Int{
            return if(Prefs.get(context).getDarkMode()){
                R.color.black
            } else {
                R.color.white
            }
        }

        fun isSystemSetToDarkMode(): Boolean{
            return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        }

    }

}