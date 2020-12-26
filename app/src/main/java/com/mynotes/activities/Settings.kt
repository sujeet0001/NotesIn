package com.mynotes.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.mynotes.R
import com.mynotes.utils.BaseActivity
import com.mynotes.utils.Prefs
import kotlinx.android.synthetic.main.settings.*

class Settings : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme()
        adjustFontScale(resources.configuration)
        setContentView(R.layout.settings)
        setViews()
        setClickListeners()
    }

    private fun setViews(){
        se_system_display_switch.isChecked = Prefs.get(applicationContext).isSystemThemeChangesAppTheme()
    }

    private fun setClickListeners(){
        se_back.setOnClickListener { onBackPressed() }
        se_change_code.setOnClickListener { startActivity(Intent(applicationContext, ChangeSecretCode::class.java)) }

        se_system_display_switch.setOnCheckedChangeListener { _, isChecked ->
            Prefs.get(applicationContext).setSystemThemeChangesAppTheme(isChecked)
            if(isChecked){
                se_dark_mode_view.visibility = View.GONE
            } else {
                se_dark_mode_view.visibility = View.VISIBLE
            }
        }

        se_dark_mode_switch.setOnCheckedChangeListener { _, isChecked ->
            Prefs.get(applicationContext).setDarkMode(isChecked)
        }

    }

}