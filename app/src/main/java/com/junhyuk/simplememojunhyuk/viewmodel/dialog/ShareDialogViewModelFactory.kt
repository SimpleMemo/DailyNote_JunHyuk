package com.junhyuk.simplememojunhyuk.viewmodel.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

/*
*
* 파일명: ShareDialogViewModelFactory
* 역할: ShareDialog 의 viewModel 이 파라미터를 소유하고 생성이 됨
* 작성자: YangJunHyuk333
*
* */

class ShareDialogViewModelFactory() : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShareDialogViewModel::class.java)){
            return ShareDialogViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}