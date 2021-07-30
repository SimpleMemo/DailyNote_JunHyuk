package com.junhyuk.simplememojunhyuk.model

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

    //데이터 전체 초기화
    fun clear(){
        title = ""
        content = ""
        position = 0
    }
}