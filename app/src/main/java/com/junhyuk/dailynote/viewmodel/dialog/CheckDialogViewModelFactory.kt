package com.junhyuk.dailynote.viewmodel.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

/*
*
* 파일명: CheckDialogViewModelFactory
* 역할: CheckDialog 의 viewModel 이 파라미터를 소유하고 생성이 됨
* 작성자: YangJunHyuk333
*
* */

class CheckDialogViewModelFactory() : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckDialogViewModel::class.java)){
            return CheckDialogViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}