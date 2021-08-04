package com.junhyuk.simplememojunhyuk.viewmodel.post

import android.annotation.SuppressLint
import android.app.Application
import android.icu.text.SimpleDateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.junhyuk.simplememojunhyuk.model.`object`.MemoObject
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

    //MemoRepository 선언 및 초기화
    private var memoRepository: MemoRepository = MemoRepository(application)

    //MemoList 불러오기
    private var memoList: LiveData<List<MemoData>> = memoRepository.getAllMemos()

    //title, content, state, position 변수 선언
    var title = MutableLiveData<String>() //title
    var content = MutableLiveData<String>() //content

    //state
    private val state = MutableLiveData<String>()
    val stateData: LiveData<String>
        get() = state

    //position
    private val position = MutableLiveData<Int>()
    val positionData: LiveData<Int>
        get() = position

    //생성자
    init {
        //시간 받아오기
        val now: Long = System.currentTimeMillis()
        val mDate = Date(now)
        val simpleDate = SimpleDateFormat("yyyy년 MM월 dd일")
        val getTime: String = simpleDate.format(mDate)
        
        //요일 변환을 위해 Calendar 선언
        val calendar = Calendar.getInstance()
        calendar.time = mDate
        
        //요일 변환
        var dayOfWeek = ""
        when (calendar.get(Calendar.DAY_OF_WEEK)) {
            1 -> dayOfWeek = "일"
            2 -> dayOfWeek = "월"
            3 -> dayOfWeek = "화"
            4 -> dayOfWeek = "수"
            5 -> dayOfWeek = "목"
            6 -> dayOfWeek = "금"
            7 -> dayOfWeek = "토"
        }
        
        //날짜를 초반 Title EditText 의 Text 값으로 설정
        title.value = getTime + " " + dayOfWeek + "요일"
    }

    //state 저장
    fun setState(state: String) {
        this.state.value = state
    }

    //position 설정
    fun setPosition(position: Int) {
        this.position.value = position
    }

    //text, content 설정
    fun setTitleAndContent(title: String, content: String){
        this.title.value = title
        this.content.value = content
    }

    fun setObject(){
        MemoObject.title = title.value.toString()
        MemoObject.content = content.value.toString()
    }

    //MemoList 모두 불러오기
    fun getAllMemo(): LiveData<List<MemoData>> {
        return memoList
    }
}