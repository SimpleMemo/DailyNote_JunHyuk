package com.junhyuk.simplememojunhyuk.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.junhyuk.simplememojunhyuk.model.MemoData
import com.junhyuk.simplememojunhyuk.model.MemoRepository

/*
*
* 파일명: PostFragmentViewModel
* 역할: PostFragment 의 ViewModel 로 View 에서 필요한 데이터를 처리
* 작성자: YangJunHyuk333
*
* */

class PostFragmentViewModel(application: Application?) : ViewModel() {

    //title, content, state 변수 선언
    private val title = MutableLiveData<String>()
    val titleData: LiveData<String>
        get() = title

    private val content = MutableLiveData<String>()
    val contentData: LiveData<String>
        get() = content

    private val state = MutableLiveData<String>()
    val stateData: LiveData<String>
        get() = state

    //state 저장
    fun setState(state: String){
        this.state.value = state
    }

    //title, content 초기화
    fun setTextValue(title: String, content: String){
        this.title.value = title
        this.content.value = content
    }

    //MemoRepository 선언 및 초기화
    private var memoRepository: MemoRepository = MemoRepository(application)

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