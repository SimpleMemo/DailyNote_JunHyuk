package com.junhyuk.simplememojunhyuk.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

/*
*
* 파일명: InputFragmentViewModelFactory
* 역할: InputFragmentViewModel 의 viewModel 이 파라미터를 소유하고 생성이 됨
* 작성자: YangJunHyuk333
*
* */

class InputFragmentViewModelFactory(private val application: Application?) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InputFragmentViewModel::class.java)){
            return InputFragmentViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}