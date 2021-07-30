package com.junhyuk.simplememojunhyuk.model

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*
*
* 파일명: MemoDataBase
* 역할: DataBase 를 생성하고 관라히는 DataBase 객체를 만들기 위한 추상 클래스
* 작성자: YangJunHyuk333
*
* */

@Database(entities = [MemoData::class], version = 1)
abstract class MemoDataBase : RoomDatabase() {
    abstract fun memoDao(): MemoDao?

    companion object {
        private var INSTANCE: MemoDataBase? = null
        fun getAppDatabase(context: Application?): MemoDataBase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context!!,
                    MemoDataBase::class.java, "jun-memo-db"
                ).build()
            }
            return INSTANCE
        }

        fun destroyINSTANCE() {
            INSTANCE = null
        }
    }
}