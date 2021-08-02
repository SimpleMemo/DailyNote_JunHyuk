package com.junhyuk.simplememojunhyuk.viewmodel.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.junhyuk.simplememojunhyuk.model.database.MemoData
import com.junhyuk.simplememojunhyuk.model.repository.MemoRepository

/*
*
* 파일명: MainActivityViewModel
* 역할: MainActivity 의 ViewModel 로 View 에서 필요한 데이터를 처리
* 작성자: YangJunHyuk333
*
* */


//MainActivityViewModel
class MainActivityViewModel(application: Application) : ViewModel() {

    //MemoRepository 선언 및 초기화
    private var memoRepository: MemoRepository = MemoRepository(application)
    //MemoList 불러오기
    private var memoList: LiveData<List<MemoData>> = memoRepository.getAllMemos()

    //MemoList 모두 불러오기
    fun getAllMemo() : LiveData<List<MemoData>>{
        return memoList
    }

}