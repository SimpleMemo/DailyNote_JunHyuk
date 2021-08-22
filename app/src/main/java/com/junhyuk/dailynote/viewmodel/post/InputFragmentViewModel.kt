package com.junhyuk.dailynote.viewmodel.post

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.lifecycle.*
import com.junhyuk.dailynote.model.`object`.MemoObject
import com.junhyuk.dailynote.model.repository.MemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/*
*
* 파일명: InputFragmentViewModel
* 역할: InputFragment 의 ViewModel 로 View 에서 필요한 데이터를 처리
* 작성자: YangJunHyuk333
*
* */

@SuppressLint("SimpleDateFormat")
@HiltViewModel
class InputFragmentViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle) : ViewModel() {

    //title, content, state, id 변수 선언
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

        if(MemoObject.state == "INSERT"){
            setDate()
        }else if (MemoObject.state == "UPDATE"){
            title.value = MemoObject.title
        }

        if(savedStateHandle.get<String>("title") != null){
            title.value = savedStateHandle.get<String>("title")
            content.value = savedStateHandle.get<String>("content")
            state.value = savedStateHandle.get<String>("state")
            id.value = savedStateHandle.get<Int>("id")
        }

    }

    //text, content 설정
    fun setValue(id: Int, title: String, content: String, state: String){
        this.id.value = id
        this.title.value = title
        this.content.value = content
        this.state.value = state
        setSavedData()
    }

    fun setObject(){
        if(id.value != null){
            MemoObject.id = id.value!!
        }
        MemoObject.title = title.value.toString()
        MemoObject.content = content.value.toString()
        MemoObject.state = state.value.toString()
        setSavedData()
    }

    private fun setSavedData(){
        savedStateHandle.set("title", title.value)
        savedStateHandle.set("content", content.value)
        savedStateHandle.set("state", state.value)
        savedStateHandle.set("id", id.value)
    }

    private fun setDate(){
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
}