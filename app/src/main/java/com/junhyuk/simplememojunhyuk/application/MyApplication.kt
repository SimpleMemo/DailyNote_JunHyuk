package com.junhyuk.simplememojunhyuk.application

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import com.junhyuk.simplememojunhyuk.model.`object`.Constants
import com.junhyuk.simplememojunhyuk.model.`object`.ThemeManager
import com.junhyuk.simplememojunhyuk.model.database.SharedPreferenceData

class MyApplication : Application() {

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()
        pref = SharedPreferenceData(this)
        if(pref.darkModeState == Constants.DARK_MODE_STATE) ThemeManager.applyTheme(pref.darkModeState)
        else ThemeManager.applyTheme(pref.darkModeState)
    }

    companion object {
        lateinit var INSTANCE: MyApplication
        fun applicationContext() : Context {
            return INSTANCE.applicationContext
        }
        lateinit var pref: SharedPreferenceData
    }

}