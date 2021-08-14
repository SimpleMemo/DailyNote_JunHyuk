package com.junhyuk.dailynote.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.junhyuk.dailynote.model.repository.MemoRepository
import com.junhyuk.dailynote.paging.MemoPageRepository

/*
*
* 파일명: MainActivityViewModelFactory
* 역할: MainActivity 의 viewModel 이 파라미터를 소유하고 생성이 됨
* 작성자: YangJunHyuk333
*
* */


class MainActivityViewModelFactory(
    private val memoPageRepository: MemoPageRepository,
    private val memoRepository: MemoRepository) : ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)){
            return MainActivityViewModel(memoPageRepository, memoRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}