package com.junhyuk.simplememojunhyuk.viewmodel.post

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.junhyuk.simplememojunhyuk.model.database.MemoData
import com.junhyuk.simplememojunhyuk.model.repository.MemoRepository

/*
*
* 파일명: InputFragmentViewModel
* 역할: InputFragment 의 ViewModel 로 View 에서 필요한 데이터를 처리
* 작성자: YangJunHyuk333
*
* */

class InputFragmentViewModel(application: Application?) : ViewModel() {

    //title, content, state 변수 선언
    var title = MutableLiveData<String>()
    var content = MutableLiveData<String>()

    private val state = MutableLiveData<String>()
    val stateData: LiveData<String>
        get() = state

    //state 저장
    fun setState(state: String){
        this.state.value = state
    }

    //MemoRepository 선언 및 초기화
    private var memoRepository: MemoRepository = MemoRepository(application)

    //MemoList 불러오기
    private var memoList: LiveData<List<MemoData>> = memoRepository.getAllMemos()

    //MemoList 모두 불러오기
    fun getAllMemo(): LiveData<List<MemoData>> {
        return memoList
    }
}