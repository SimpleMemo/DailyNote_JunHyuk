package com.junhyuk.simplememojunhyuk.view.setting

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.databinding.DataBindingUtil
import com.junhyuk.simplememojunhyuk.R
import com.junhyuk.simplememojunhyuk.application.MyApplication
import com.junhyuk.simplememojunhyuk.databinding.ActivitySettingBinding
import com.junhyuk.simplememojunhyuk.model.`object`.ThemeManager

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@SettingActivity, R.layout.activity_setting)

        binding.apply {
            backButton.setOnClickListener {
                finish()
            }

            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_NO -> lightCheck.isChecked = true
                Configuration.UI_MODE_NIGHT_YES -> darkCheck.isChecked = true
            }

            selectedTheme.setOnCheckedChangeListener { group, checkedId ->
                when(checkedId){
                    R.id.lightCheck -> {
                        ThemeManager.applyTheme(ThemeManager.LIGHT)
                        MyApplication.pref.darkModeState = ThemeManager.LIGHT
                    }
                    R.id.darkCheck -> {
                        ThemeManager.applyTheme(ThemeManager.DARK)
                        MyApplication.pref.darkModeState = ThemeManager.DARK
                    }
                }
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}