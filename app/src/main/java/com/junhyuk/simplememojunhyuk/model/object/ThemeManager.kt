package com.junhyuk.simplememojunhyuk.model.`object`

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate

/*
*
* 파일명: ThemeManager
* 역할: Android Application 의 Theme 값을 설정하는 곳
* 작성자: YangJunHyuk333
*
* */

object ThemeManager {
    const val LIGHT = "LIGHT"
    const val DARK = "DARK"
    const val DEFAULT = "DEFAULT"

    fun applyTheme(themeMode: String) {
        when (themeMode) {
            LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            DEFAULT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            else ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                }
        }
    }
}