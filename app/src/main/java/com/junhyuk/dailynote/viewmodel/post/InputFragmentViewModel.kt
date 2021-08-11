package com.junhyuk.dailynote.viewmodel.post

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.junhyuk.dailynote.model.`object`.MemoObject
import java.util.*

/*
*
* 파일명: InputFragmentViewModel
* 역할: InputFragment 의 ViewModel 로 View 에서 필요한 데이터를 처리
* 작성자: YangJunHyuk333
*
* */

@SuppressLint("SimpleDateFormat")
class InputFragmentViewModel : ViewModel() {

    //title, content, state, position 변수 선언
    var title = MutableLiveData<String>() //title
    var content = MutableLiveData<String>() //content

    //state
    private val state = MutableLiveData<String>()
    val stateData: LiveData<String>
        get() = state

    //id
    private val id = MutableLiveData<Int>()

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

    //text, content 설정
    fun setTitleAndContent(id: Int, title: String, content: String, state: String){
        this.id.value = id
        this.title.value = title
        this.content.value = content
        this.state.value = state
    }

    fun setObject(){
        if(id.value != null){
            MemoObject.id = id.value!!
        }
        MemoObject.title = title.value.toString()
        MemoObject.content = content.value.toString()
        MemoObject.state = state.value.toString()
    }
}