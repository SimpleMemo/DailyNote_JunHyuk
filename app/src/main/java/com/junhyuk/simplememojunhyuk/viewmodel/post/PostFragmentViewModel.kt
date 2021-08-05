package com.junhyuk.simplememojunhyuk.viewmodel.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.junhyuk.simplememojunhyuk.application.MyApplication
import com.junhyuk.simplememojunhyuk.model.`object`.MemoObject
import com.junhyuk.simplememojunhyuk.model.database.MemoData

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

    //dataIndex
    private val dataIndex = MutableLiveData<Int>()
    val dataIndexData: LiveData<Int>
        get() = dataIndex

    //state 저장
    fun setState(state: String){
        this.state.value = state
    }

    //dataIndex 저장
    fun setDataIndex(dataIndex: Int){
        this.dataIndex.value = dataIndex
    }

    //title, content 초기화
    fun setTextValue(title: String, content: String){
        this.title.value = title
        this.content.value = content
    }

    //Object 저장
    fun setObject(){
        MemoObject.title = title.value.toString()
        MemoObject.content = content.value.toString()
    }

    fun setPosAndIndexObject(){
        MemoObject.state = stateData.value.toString()
        MemoObject.position = dataIndexData.value!!
    }
}