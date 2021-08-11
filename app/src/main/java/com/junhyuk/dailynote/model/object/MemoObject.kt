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
    var id: Int = 0 //ID
    var title: String = "" //titleData
    var content: String = "" //contentData
    var state: String = "" //Post Or Update State
    var bitmap: Bitmap? = null //bitmapData

    //데이터 전체 설정
    fun setAll(id: Int, title: String, content: String, state: String){
        this.title = title
        this.content = content
        this.state = state
        this.bitmap = null
        this.id = id
    }

    //데이터 전체 초기화
    fun clear(){
        id = 0
        title = ""
        content = ""
        state = ""
        bitmap = null
    }
}