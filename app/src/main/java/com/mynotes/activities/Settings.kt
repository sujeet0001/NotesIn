package com.mynotes.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import com.mynotes.R
import com.mynotes.utils.BaseActivity
import com.mynotes.utils.DisplayUtils
import com.mynotes.utils.Prefs
import kotlinx.android.synthetic.main.settings.*

class Settings : BaseActivity() {

    var currentTheme: Int = 0

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setTheme(DisplayUtils.getTheme(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentTheme = DisplayUtils.getTheme(applicationContext)
        setTheme(currentTheme)
        adjustFontScale(resources.configuration)
        setContentView(R.layout.settings)
        setViews()
        setClickListeners()
    }

    private fun setViews() {
        val isSystemThemeChangesAppTheme =
            Prefs.get(applicationContext).isSystemThemeChangesAppTheme()
        se_system_display_switch.isChecked = isSystemThemeChangesAppTheme
        if (!isSystemThemeChangesAppTheme) {
            se_dark_mode_view.visibility = View.VISIBLE
        }
        se_dark_mode_switch.isChecked = Prefs.get(applicationContext).isDarkMode()
    }

    private fun setClickListeners() {
        se_back.setOnClickListener { onBackPressed() }
        se_change_code.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    ChangeSecretCode::class.java
                )
            )
        }

        se_system_display_switch.setOnCheckedChangeListener { _, isChecked ->
            Prefs.get(applicationContext).setSystemThemeChangesAppTheme(isChecked)
            if (isChecked) {
                se_dark_mode_view.visibility = View.GONE
            } else {
                se_dark_mode_view.visibility = View.VISIBLE
            }
            if (DisplayUtils.getTheme(applicationContext) != currentTheme) {
                currentTheme = DisplayUtils.getTheme(applicationContext)
                setTheme(currentTheme)
            }
        }

        se_dark_mode_switch.setOnCheckedChangeListener { _, isChecked ->
            Prefs.get(applicationContext).setDarkMode(isChecked)
            setTheme(DisplayUtils.getTheme(applicationContext))
        }
    }
}