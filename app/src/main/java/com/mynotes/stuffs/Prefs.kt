package com.mynotes.stuffs

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context){

    private var sp: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    private var ed: SharedPreferences.Editor

    init {
        ed = sp.edit()
        ed.apply()
    }

    fun getString(key: String): String{
        return sp.getString(key, "")
    }

    fun setString(key: String, value: String){
        ed.putString(key, value)
        ed.apply()
    }

    fun getInt(key: String): Int{
        return sp.getInt(key, 0)
    }

    fun setInt(key: String, value: Int){
        ed.putInt(key, value)
        ed.apply()
    }

    fun getBool(key: String): Boolean{
        return sp.getBoolean(key, false)
    }

    fun setBool(key: String, value: Boolean){
        ed.putBoolean(key, value)
        ed.apply()
    }

    fun getPasscode(): String{
        return sp.getString(Strs.PASSCODE, "1234")
    }

    fun setPasscode(value: String){
        ed.putString(Strs.PASSCODE, value)
        ed.apply()
    }
}