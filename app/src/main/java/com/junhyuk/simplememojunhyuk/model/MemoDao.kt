package com.junhyuk.simplememojunhyuk.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/*
*
* 파일명: MemoDao
* 역할: MemoDataBase 접근할 수 있는 메서드 정의 인터페이스
* 작성자: YangJunHyuk333
*
* */

@Dao
interface MemoDao {

    @Query("SELECT * FROM memo")
    fun getAll(): LiveData<List<MemoData>> //모든 Data 를 불러옴

    @Query("UPDATE 'memo' SET title = :titleEdit, content = :contentEdit WHERE memoId = :id")
    fun update(id: Int?, titleEdit: String, contentEdit: String) //메모장 Update

    @Query("DELETE FROM 'memo' WHERE memoId = :id")
    fun delete(id: Int?) //메모장 Delete

    @Insert
    fun insert(memo: MemoData) //메모장 Insert

    @Delete
    fun deleteAll(memo: MemoData?) //메모장 전체 Delete

}