package com.junhyuk.simplememojunhyuk.viewmodel.post

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

/*
*
* 파일명: PostActivityViewModelFactory
* 역할: PostActivity 의 viewModel 이 파라미터를 소유하고 생성이 됨
* 작성자: YangJunHyuk333
*
* */


class PostActivityViewModelFactory() : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostActivityViewModel::class.java)){
            return PostActivityViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}