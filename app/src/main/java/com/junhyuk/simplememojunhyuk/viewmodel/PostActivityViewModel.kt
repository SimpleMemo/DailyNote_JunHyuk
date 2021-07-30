package com.junhyuk.simplememojunhyuk.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.junhyuk.simplememojunhyuk.model.MemoData
import com.junhyuk.simplememojunhyuk.model.MemoRepository

/*
*
* 파일명: PostActivityViewModel
* 역할: PostActivity 의 ViewModel 로 View 에서 필요한 데이터를 처리
* 작성자: YangJunHyuk333
*
* */

//PostActivityViewModel
class PostActivityViewModel(application: Application) : ViewModel() {

    //title, content 변수 선언
    var title = MutableLiveData<String>()
    var content = MutableLiveData<String>()

    //MemoRepository 선언 및 초기화
    private var memoRepository: MemoRepository = MemoRepository(application)
    //MemoList 불러오기
    private var memoList: LiveData<List<MemoData>> = memoRepository.getAllMemos()

    //MemoList 모두 불러오기
    fun getAllMemo() : LiveData<List<MemoData>>{
        return memoList
    }

    //Memo DB 수정
    // (UPDATE 'memo' SET title = :titleEdit, content = :contentEdit WHERE memoId = :id)
    fun update(position: Int?, title: String, content: String) {
        memoRepository.update(position, title, content)
    }

    //Memo DB 삽입
    fun insert(memo: MemoData) {
        memoRepository.insert(memo)
    }

}