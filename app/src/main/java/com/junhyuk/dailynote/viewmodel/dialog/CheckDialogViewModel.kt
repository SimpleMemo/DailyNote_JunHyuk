package com.junhyuk.dailynote.viewmodel.dialog

import androidx.lifecycle.ViewModel
import com.junhyuk.dailynote.application.MyApplication

/*
*
* 파일명: CheckDialogViewModel
* 역할: CheckDialog 의 ViewModel 로 View 에서 필요한 데이터를 처리
* 작성자: YangJunHyuk333
*
* */

class CheckDialogViewModel() : ViewModel() {

    //Memo 삭제
    fun deleteMemo(memoId: Int?){
        MyApplication.memoRepository.deleteMemo(memoId)
    }

}