package com.junhyuk.dailynote.model.database

import androidx.annotation.Nullable
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
data class MemoData(
    @PrimaryKey
    var memoId: Int, //기본키, memoId

    @ColumnInfo(name = "title")
    val memoTitle: String, //title

    @ColumnInfo(name = "content")
    val memoContent: String, //content

    @ColumnInfo(name = "updated", defaultValue = "0")
    val updated: Int //updated
)
