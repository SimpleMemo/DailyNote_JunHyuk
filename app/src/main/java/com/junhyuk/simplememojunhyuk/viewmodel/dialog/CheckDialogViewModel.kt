package com.junhyuk.simplememojunhyuk.viewmodel.dialog

import android.app.Application
import androidx.lifecycle.ViewModel
import com.junhyuk.simplememojunhyuk.model.repository.MemoRepository

/*
*
* 파일명: CheckDialogViewModel
* 역할: CheckDialog 의 ViewModel 로 View 에서 필요한 데이터를 처리
* 작성자: YangJunHyuk333
*
* */

class CheckDialogViewModel(application: Application?) : ViewModel() {

    //MemoRepository 선언 및 초기화
    private var memoRepository: MemoRepository = MemoRepository(application)

    //Memo 삭제
    fun deleteMemo(memoId: Int?){
        memoRepository.deleteMemo(memoId)
    }

}