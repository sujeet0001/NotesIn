package com.mynotes.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import com.mynotes.R
import com.mynotes.utils.BaseActivity
import com.mynotes.utils.DisplayUtils
import com.mynotes.utils.Prefs
import kotlinx.android.synthetic.main.settings.*

class Settings : BaseActivity() {

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        recreateActivityOnThemeChange(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewConfigs(resources.configuration, DisplayUtils.getTheme(applicationContext))
        setContentView(R.layout.settings)
        setViews()
        setClickListeners()
    }

    private fun setViews() {
        if(android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.P){
            val isSystemThemeChangesAppTheme =
                Prefs.get(applicationContext).isSystemThemeChangesAppTheme()
            se_system_display_switch.isChecked = isSystemThemeChangesAppTheme
            if (!isSystemThemeChangesAppTheme) {
                se_dark_mode_view.visibility = View.VISIBLE
            }
        } else {
            se_system_display_view.visibility = View.GONE
            se_dark_mode_view.visibility = View.VISIBLE
        }
        se_dark_mode_switch.isChecked = Prefs.get(applicationContext).isDarkMode()
    }

    private fun setClickListeners() {
        se_back.setOnClickListener { onBackPressed() }
        se_change_code.setOnClickListener {
            startActivity(Intent(applicationContext, ChangeSecretCode::class.java))
        }

        se_system_display_switch.setOnCheckedChangeListener { _, isChecked ->
            Prefs.get(applicationContext).setSystemThemeChangesAppTheme(isChecked)
            if (isChecked) {
                se_dark_mode_view.visibility = View.GONE
            } else {
                se_dark_mode_view.visibility = View.VISIBLE
            }
            recreateActivityOnThemeChange(this)
        }

        se_dark_mode_switch.setOnCheckedChangeListener { _, isChecked ->
            Prefs.get(applicationContext).setDarkMode(isChecked)
            recreateActivityOnThemeChange(this)
        }
    }

    override fun onRestart() {
        super.onRestart()
        recreateActivityOnThemeChange(this)
    }

}