package com.notesin.utils

import android.content.Context
import android.content.SharedPreferences
import com.notesin.utils.Constants.Companion.PREF_DARK_MODE
import com.notesin.utils.Constants.Companion.PREF_ENABLE_SECRET_CODE
import com.notesin.utils.Constants.Companion.PREF_SECRET_CODE
import com.notesin.utils.Constants.Companion.PREF_SYS_THEME_CHANGES_APP_THEME

class Prefs {

    companion object {

        @Volatile private var prefs: Prefs? = null
        private lateinit var sp: SharedPreferences
        private lateinit var ed: SharedPreferences.Editor

        fun get(context: Context): Prefs {
            synchronized(this){
                if (prefs == null) {
                    prefs = Prefs()
                    sp = context.getSharedPreferences("NotesPrefs", Context.MODE_PRIVATE)
                    ed = sp.edit()
                    ed.apply()
                }
            }
            return prefs!!
        }
    }

    fun getString(key: String): String? {
        return sp.getString(key, "")
    }

    fun setString(key: String, value: String) {
        ed.putString(key, value)
        ed.apply()
    }

    fun getInt(key: String): Int {
        return sp.getInt(key, 0)
    }

    fun setInt(key: String, value: Int) {
        ed.putInt(key, value)
        ed.apply()
    }

    fun getBool(key: String): Boolean {
        return sp.getBoolean(key, false)
    }

    fun setBool(key: String, value: Boolean) {
        ed.putBoolean(key, value)
        ed.apply()
    }

    fun getSecretCode(): String? {
        return sp.getString(PREF_SECRET_CODE, "0001")
    }

    fun setSecretCode(value: String) {
        ed.putString(PREF_SECRET_CODE, value)
        ed.apply()
    }

    fun isDarkMode(): Boolean {
        return sp.getBoolean(PREF_DARK_MODE, false)
    }

    fun setDarkMode(isDarkMode: Boolean) {
        ed.putBoolean(PREF_DARK_MODE, isDarkMode)
        ed.apply()
    }

    fun isSystemThemeChangesAppTheme(): Boolean {
        if(android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.P){
            return sp.getBoolean(PREF_SYS_THEME_CHANGES_APP_THEME, true)
        }
        return sp.getBoolean(PREF_SYS_THEME_CHANGES_APP_THEME, false)
    }

    fun setSystemThemeChangesAppTheme(bool: Boolean) {
        ed.putBoolean(PREF_SYS_THEME_CHANGES_APP_THEME, bool)
        ed.apply()
    }

    fun isSecretCodeEnabled(): Boolean {
        return sp.getBoolean(PREF_ENABLE_SECRET_CODE, true)
    }

    fun enableSecretCode(bool: Boolean){
        ed.putBoolean(PREF_ENABLE_SECRET_CODE, bool)
        ed.apply()
    }
}