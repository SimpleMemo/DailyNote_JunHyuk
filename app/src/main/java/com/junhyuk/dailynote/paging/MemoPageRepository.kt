package com.junhyuk.dailynote.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.junhyuk.dailynote.model.database.MemoDao
import com.junhyuk.dailynote.model.database.MemoData
import kotlinx.coroutines.flow.Flow

/*
*
* 파일명: MemoPageRepository
* 역할: Memo DataBase 에 접근해 페이징을 처리할 때 필요한 데이터를 가져욤
* 작성자: YangJunHyuk333
*
* */

class MemoPageRepository (private val memoDao: MemoDao){

    fun getTodoContentItemsByPaging(): Flow<PagingData<MemoData>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {MemoPagingSource(memoDao)}
        ).flow
    }

}