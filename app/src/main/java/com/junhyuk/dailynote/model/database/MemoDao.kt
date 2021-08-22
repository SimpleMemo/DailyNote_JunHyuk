package com.junhyuk.dailynote.model.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/*
*
* 파일명: MemoDao
* 역할: MemoDataBase 접근할 수 있는 메서드 정의 인터페이스
* 작성자: YangJunHyuk333
*
* */

@Dao
interface MemoDao {

    @Query("SELECT * FROM memo ORDER BY memoId DESC")
    fun getAll(): Flow<List<MemoData>> //모든 Data 를 불러옴

    @Query("SELECT * FROM memo ORDER BY updated DESC LIMIT :loadSize OFFSET (:page-1) * :loadSize")
    suspend fun getMemoContentsByPaging(page: Int, loadSize: Int): List<MemoData> //paging

    @Update
    suspend fun update(memo: MemoData) //메모장 Update

    @Insert
    suspend fun insert(memo: MemoData) //메모장 Insert

    @Delete
    suspend fun delete(memo: MemoData) //메모장 전체 Delete

}