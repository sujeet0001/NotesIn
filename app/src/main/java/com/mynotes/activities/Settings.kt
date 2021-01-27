package com.mynotes.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import com.mynotes.R
import com.mynotes.utils.BaseActivity
import com.mynotes.utils.Constants
import com.mynotes.utils.Prefs
import kotlinx.android.synthetic.main.settings.*

class Settings : BaseActivity() {

    private val reqCode = 0

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        recreateActivityOnThemeChange(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewConfigs(resources.configuration, R.layout.settings)
        setViews()
        setClickListeners()
    }

    private fun setViews() {

        setSecretCodeViews()

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
            goToSecretCode(true)
        }

        se_enable_disable_view.setOnClickListener {
            goToSecretCode(false)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == reqCode){
            setSecretCodeViews()
        }
    }

    private fun setSecretCodeViews(){
        if(Prefs.get(applicationContext).isSecretCodeEnabled()){
            se_enable_disable_code.text = Constants.DISABLE_SECRET_CODE
            se_enable_disable_code_sub.text = Constants.DISABLE_SECRET_CODE_SUB
            se_change_code.visibility = View.VISIBLE
            if(Prefs.get(applicationContext).getBool(Constants.PREF_SECRET_CODE_SET)){
                se_change_code.text = Constants.CHANGE_SECRET_CODE
            } else {
                se_change_code.text = Constants.SET_SECRET_CODE
            }
        } else {
            se_enable_disable_code.text = Constants.ENABLE_SECRET_CODE
            se_enable_disable_code_sub.text = Constants.ENABLE_SECRET_CODE_SUB
            se_change_code.visibility = View.GONE
        }
    }

    private fun goToSecretCode(fromChangeSecretCode: Boolean){
        val intent = Intent(applicationContext, ChangeSecretCode::class.java)
        intent.putExtra(Constants.FROM_CHANGE_SECRET_CODE, fromChangeSecretCode)
        startActivityForResult(intent, reqCode)
    }

}