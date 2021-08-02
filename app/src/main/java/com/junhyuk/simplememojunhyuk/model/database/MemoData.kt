package com.junhyuk.simplememojunhyuk.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
*
* 파일명: MemoData
* 역할: 관련이 있는 속성들이 모여 하나의 정보 단위를 이룬 개체(Entity)를 선언
* 작성자: YangJunHyuk333
*
* */

@Entity(tableName = "memo")
class MemoData(
    @ColumnInfo(name = "title") val memoTitle: String, //title
    @ColumnInfo(name = "content") val memoContent: String //content
) {
    @PrimaryKey(autoGenerate = true) var memoId: Int = 0 //기본키, memoId
}
