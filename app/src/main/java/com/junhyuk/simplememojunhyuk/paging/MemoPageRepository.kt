package com.junhyuk.simplememojunhyuk.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.junhyuk.simplememojunhyuk.model.database.MemoDao
import com.junhyuk.simplememojunhyuk.model.database.MemoData
import kotlinx.coroutines.flow.Flow

class MemoPageRepository (private val memoDao: MemoDao){

    fun getTodoContentItemsByPaging(): Flow<PagingData<MemoData>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {MemoPagingSource(memoDao)}
        ).flow
    }

}