package com.junhyuk.simplememojunhyuk.viewmodel.post

import android.annotation.SuppressLint
import android.app.Application
import android.icu.text.SimpleDateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.junhyuk.simplememojunhyuk.model.database.MemoData
import com.junhyuk.simplememojunhyuk.model.repository.MemoRepository
import java.util.*

/*
*
* 파일명: InputFragmentViewModel
* 역할: InputFragment 의 ViewModel 로 View 에서 필요한 데이터를 처리
* 작성자: YangJunHyuk333
*
* */

@SuppressLint("SimpleDateFormat")
class InputFragmentViewModel(application: Application?) : ViewModel() {

    //title, content, state, position 변수 선언
    var title = MutableLiveData<String>()
    var content = MutableLiveData<String>()

    private val state = MutableLiveData<String>()
    val stateData: LiveData<String>
        get() = state

    private val position = MutableLiveData<Int>()
    val positionData: LiveData<Int>
        get() = position

    //state 저장
    fun setState(state: String){
        this.state.value = state
    }
    
    //position 설정
    fun setPosition(position: Int){
        this.position.value = position
    }

    //생성자
    init {
        val now: Long = System.currentTimeMillis()
        val mDate = Date(now)
        val simpleDate = SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분 ss초")
        val getTime: String = simpleDate.format(mDate)
        title.value = getTime
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