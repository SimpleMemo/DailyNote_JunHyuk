package com.junhyuk.dailynote.model.repository

import android.app.Application
import com.junhyuk.dailynote.model.database.MemoDao
import com.junhyuk.dailynote.model.database.MemoData
import com.junhyuk.dailynote.model.database.MemoDataBase

/*
*
* 파일명: MemoRepository
* 역할: Memo DataBase 에 접근하는 중간 매개체
* 작성자: YangJunHyuk333
*
* */

class MemoRepository(application: Application?) {

    //선언
    private lateinit var memoDao: MemoDao

    //초기화
    init {
        val db: MemoDataBase? = MemoDataBase.getAppDatabase(application)
        if (db != null) {
            memoDao = db.memoDao()!!
        }
    }

    //메모 삽입
    suspend fun insert(memo: MemoData) {
        memoDao.insert(memo)
    }

    //메모 수정
    suspend fun update(memo: MemoData){
        memoDao.update(memo)
    }

    //메모 삭제
    fun deleteMemo(memo: MemoData){
        memoDao.delete(memo)
    }

    fun getDao(): MemoDao {
        return memoDao
    }

}