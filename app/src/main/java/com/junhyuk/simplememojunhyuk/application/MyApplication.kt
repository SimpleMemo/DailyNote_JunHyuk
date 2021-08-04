package com.junhyuk.simplememojunhyuk.application

import android.app.Application
import android.content.Context
import com.junhyuk.simplememojunhyuk.model.`object`.Constants
import com.junhyuk.simplememojunhyuk.model.`object`.ThemeManager
import com.junhyuk.simplememojunhyuk.model.database.SharedPreferenceData
import com.junhyuk.simplememojunhyuk.model.repository.MemoRepository

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

        pref = SharedPreferenceData(this)
        if(pref.darkModeState == Constants.DARK_MODE_STATE) ThemeManager.applyTheme(pref.darkModeState)
        else ThemeManager.applyTheme(pref.darkModeState)

        memoRepository = MemoRepository(INSTANCE)
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