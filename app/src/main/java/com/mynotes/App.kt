package com.mynotes

import android.app.Application
import android.content.res.Configuration
import com.mynotes.utils.ViewUtils

class App: Application() {

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if(ViewUtils.isSystemSetToDarkMode()){
            setTheme(R.style.AppTheme)
        } else {
            setTheme(R.style.AppThemeLight)
        }
    }

    override fun onCreate() {
        super.onCreate()
        if(ViewUtils.isSystemSetToDarkMode()){
            setTheme(R.style.AppTheme)
        } else {
            setTheme(R.style.AppThemeLight)
        }
    }

}