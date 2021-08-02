package com.junhyuk.simplememojunhyuk.application

import android.app.Application
import android.content.Context

class MyApplication : Application() {

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        lateinit var INSTANCE: MyApplication
        fun applicationContext() : Context {
            return INSTANCE.applicationContext
        }
    }

}