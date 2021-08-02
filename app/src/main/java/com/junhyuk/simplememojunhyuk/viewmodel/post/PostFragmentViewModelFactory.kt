package com.junhyuk.simplememojunhyuk.viewmodel.post

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

/*
*
* 파일명: PostFragmentViewModelFactory
* 역할: PostFragment 의 viewModel 이 파라미터를 소유하고 생성이 됨
* 작성자: YangJunHyuk333
*
* */

class PostFragmentViewModelFactory(private val application: Application?) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostFragmentViewModel::class.java)){
            return PostFragmentViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}