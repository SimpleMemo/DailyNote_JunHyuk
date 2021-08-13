package com.junhyuk.dailynote.model.repository

import android.app.Application
import com.junhyuk.dailynote.model.database.MemoDao
import com.junhyuk.dailynote.model.database.MemoData
import com.junhyuk.dailynote.model.database.MemoDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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
    private var memoList: Flow<List<MemoData>>

    //초기화
    init {
        val db: MemoDataBase? = MemoDataBase.getAppDatabase(application)
        if (db != null) {
            memoDao = db.memoDao()!!
        }
        memoList = memoDao.getAll() //메모 전체 불러오기
    }

    //메모 전체를 return
    fun getAllDiary(): Flow<List<MemoData>>{
        return memoList
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
        CoroutineScope(Dispatchers.IO).launch {
            memoDao.delete(memo)
        }
    }

    fun getDao(): MemoDao {
        return memoDao
    }

}