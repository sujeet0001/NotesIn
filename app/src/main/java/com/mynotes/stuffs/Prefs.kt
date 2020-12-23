package com.mynotes.utils

import android.content.Context
import android.content.SharedPreferences
import com.mynotes.utils.Constants.Companion.SECRET_CODE

class Prefs {

    companion object {

        private var prefs: Prefs? = null
        private lateinit var sp: SharedPreferences
        private lateinit var ed: SharedPreferences.Editor

        fun getPrefs(context: Context): Prefs {
            if (prefs == null) {
                prefs = Prefs()
                sp = context.getSharedPreferences("NotesPrefs", Context.MODE_PRIVATE)
                ed = sp.edit()
                ed.apply()
            }
            return prefs!!
        }
    }

    fun getString(key: String): String {
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

    fun getSecretCode(): String {
        return sp.getString(SECRET_CODE, "1234")
    }

    fun setSecretCode(value: String) {
        ed.putString(SECRET_CODE, value)
        ed.apply()
    }
}