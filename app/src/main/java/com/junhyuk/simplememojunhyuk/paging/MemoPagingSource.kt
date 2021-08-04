package com.junhyuk.simplememojunhyuk.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.junhyuk.simplememojunhyuk.model.database.MemoDao
import com.junhyuk.simplememojunhyuk.model.database.MemoData

class MemoPagingSource (
    private val dao: MemoDao
): PagingSource<Int, MemoData>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MemoData> {
        val page = params.key ?: 1
        return try {
            val items = dao.getMemoContentsByPaging(page, params.loadSize)
            LoadResult.Page(
                data = items,
                prevKey = if(page == 1) null else page - 1,
                nextKey = if(items.isEmpty()) null else page + (params.loadSize / 10)
            )
        } catch (e: Exception){
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MemoData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}