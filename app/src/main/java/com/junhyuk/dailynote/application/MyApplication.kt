package com.junhyuk.dailynote.application

import android.app.Application
import android.content.Context
import com.junhyuk.dailynote.model.`object`.Constants
import com.junhyuk.dailynote.model.`object`.ThemeManager
import com.junhyuk.dailynote.model.database.SharedPreferenceData
import com.junhyuk.dailynote.model.repository.MemoRepository

/*
*
* 파일명: MyApplication
* 역할: Application Component 사이에서 공동으로 멤버들을 사용할 수 있게 해줌
* 작성자: YangJunHyuk333
*
* */

class MyApplication : Application() {

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()

        //SharedPreference 설정
        pref = SharedPreferenceData(this)
        if(pref.darkModeState == Constants.DARK_MODE_STATE) ThemeManager.applyTheme(
            pref.darkModeState)
        else ThemeManager.applyTheme(pref.darkModeState)

        memoRepository = MemoRepository(
            INSTANCE
        ) //Repository 선언
    }

    companion object {
        lateinit var INSTANCE: MyApplication
        fun applicationContext() : Context {
            return INSTANCE.applicationContext
        }
        lateinit var pref: SharedPreferenceData
        lateinit var memoRepository: MemoRepository
    }

}