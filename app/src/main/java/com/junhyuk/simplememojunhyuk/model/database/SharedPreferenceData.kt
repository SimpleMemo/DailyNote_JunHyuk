package com.junhyuk.simplememojunhyuk.model.database

import android.content.Context
import android.content.SharedPreferences
import com.junhyuk.simplememojunhyuk.model.`object`.Constants

class SharedPreferenceData(context: Context) {

    private val PREF_FILE_NAME = "darkModeInfo"

    val pref: SharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, 0)

    var darkModeState: String
        get() = pref.getString(Constants.DARK_MODE_KEY, Constants.DARK_MODE_STATE)!!
        set(value) = pref.edit().putString(Constants.DARK_MODE_KEY, value).apply()

}