package com.junhyuk.dailynote.viewmodel.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.junhyuk.dailynote.model.`object`.MemoObject

/*
*
* 파일명: PostFragmentViewModel
* 역할: PostFragment 의 ViewModel 로 View 에서 필요한 데이터를 처리
* 작성자: YangJunHyuk333
*
* */

class PostFragmentViewModel : ViewModel() {

    //title, content, state, dataIndex 변수 선언

    //title
    private val title = MutableLiveData<String>()
    val titleData: LiveData<String>
        get() = title

    //content
    private val content = MutableLiveData<String>()
    val contentData: LiveData<String>
        get() = content

    //state
    private val state = MutableLiveData<String>()
    val stateData: LiveData<String>
        get() = state

    //id
    private val id = MutableLiveData<Int>()

    //title, content 초기화
    fun setValue(id: Int, title: String, content: String, state: String){
        this.id.value = id
        this.title.value = title
        this.content.value = content
        this.state.value = state
    }

    //Object 저장
    fun setObject(){
        if(id.value != null){
            MemoObject.id = id.value!!
        }
        MemoObject.title = title.value.toString()
        MemoObject.content = content.value.toString()
        MemoObject.state = stateData.value.toString()
    }
}