package com.junhyuk.dailynote.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.junhyuk.dailynote.R
import com.junhyuk.dailynote.application.MyApplication
import com.junhyuk.dailynote.databinding.DialogCheckBinding
import com.junhyuk.dailynote.model.`object`.MemoObject
import com.junhyuk.dailynote.model.database.MemoData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
*
* 파일명: CheckDialog
* 역할: 정말 삭제할 것인지 묻는 Dialog
* 작성자: YangJunHyuk333
*
* */

class CheckDialog : BottomSheetDialogFragment() {

    //binding, viewModel, viewModelFactory 선언
    private val binding by lazy { DialogCheckBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //view 접근
        with(binding) {

            //deleteButtonClickAction
            deleteButton.setOnClickListener {

                //getMemoId
                val memoId: Int? = tag?.toInt()

                //delete
                CoroutineScope(Dispatchers.IO).launch {
                    if(tag?.isNotEmpty() == true){
                        deleteMemo(MemoData(memoId!!, MemoObject.title,  MemoObject.content))
                        dismiss()
                    }else{
                        Toast.makeText(activity, "Error", Toast.LENGTH_LONG).show()
                        dismiss()
                    }
                }
            }

            //cancelButtonClickAction
            cancelButton.setOnClickListener {
                dismiss()
            }
        }

        return binding.root
    }

    //Theme 적용
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    //Memo 삭제
    private fun deleteMemo(memo: MemoData){
        MyApplication.memoRepository.deleteMemo(memo)
    }

}