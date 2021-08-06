package com.junhyuk.dailynote.model.`object`

import android.graphics.Bitmap

/*
*
* 파일명: MemoObject
* 역할: Memo 의 각 요소를 불러와서 수정을 할때 잠깐 쓰이는 Object 모든 활동이 끝나고 수정이 완료되면 초기화 된다.
* 작성자: YangJunHyuk333
*
* */

object MemoObject {
    var title: String = "" //titleData
    var content: String = "" //contentData
    var position: Int = 0 //positionData
    var dataIndex: Int = 0 //dataIndex
    var state: String = "" //Post Or Update State
    var bitmap: Bitmap? = null //bitmapData

    //데이터 전체 설정
    fun setAll(title: String, content: String, position: Int, dataIndex: Int, state: String){
        this.title = title
        this.content = content
        this.position = position
        this.dataIndex = dataIndex
        this.state = state
        this.bitmap = null
    }

    //데이터 전체 초기화
    fun clear(){
        title = ""
        content = ""
        state = ""
        position = 0
    }
}