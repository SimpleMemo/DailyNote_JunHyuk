package com.junhyuk.dailynote.model.database

import android.content.Context
import android.content.SharedPreferences
import com.junhyuk.dailynote.model.`object`.Constants

/*
*
* 파일명: SharedPreferenceData
* 역할: SharedPreference 를 생성하고 관라히는 클래스
* 작성자: YangJunHyuk333
*
* */

class SharedPreferenceData(context: Context) {

    private val prefFileName = "darkModeInfo"

    private val pref: SharedPreferences = context.getSharedPreferences(prefFileName, 0)

    var darkModeState: String
        get() = pref.getString(Constants.DARK_MODE_KEY, Constants.DARK_MODE_STATE)!!
        set(value) = pref.edit().putString(Constants.DARK_MODE_KEY, value).apply()

}