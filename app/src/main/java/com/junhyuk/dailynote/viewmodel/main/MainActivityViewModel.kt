package com.junhyuk.dailynote.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.junhyuk.dailynote.application.MyApplication
import com.junhyuk.dailynote.model.database.MemoData
import com.junhyuk.dailynote.paging.MemoPageRepository
import kotlinx.coroutines.flow.Flow

/*
*
* 파일명: MainActivityViewModel
* 역할: MainActivity 의 ViewModel 로 View 에서 필요한 데이터를 처리
* 작성자: YangJunHyuk333
*
* */


//MainActivityViewModel
class MainActivityViewModel: ViewModel() {

    //MemoList 불러오기
    private var memoList: Flow<List<MemoData>> = MyApplication.memoRepository.getAllDiary()

    //pagingRepository 선언 및 초기화
    private var memoPageRepository: MemoPageRepository = MemoPageRepository(MyApplication.memoRepository.getDao())

    //Diary 데이터
    fun getAllDiary() : Flow<List<MemoData>>{
        return memoList
    }

    fun getContent(): Flow<PagingData<MemoData>> {
        return memoPageRepository.getDiaryContentItemsByPaging().cachedIn(viewModelScope)
    }

}