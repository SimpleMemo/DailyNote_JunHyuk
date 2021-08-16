package com.junhyuk.dailynote.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.junhyuk.dailynote.model.database.MemoData
import com.junhyuk.dailynote.model.repository.MemoRepository
import com.junhyuk.dailynote.paging.MemoPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/*
*
* 파일명: MainActivityViewModel
* 역할: MainActivity 의 ViewModel 로 View 에서 필요한 데이터를 처리
* 작성자: YangJunHyuk333
*
* */


//MainActivityViewModel
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val memoPageRepository: MemoPageRepository,
    private val memoRepository: MemoRepository): ViewModel() {

    val diaryData = liveData {
        memoRepository.getAllDiary().collect { emit(it) }
    }

    fun getContent(): Flow<PagingData<MemoData>> {
        return memoPageRepository.getDiaryContentItemsByPaging().cachedIn(viewModelScope)
    }

}