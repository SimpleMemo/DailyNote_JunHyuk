package com.junhyuk.dailynote.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.junhyuk.dailynote.R
import com.junhyuk.dailynote.databinding.DialogDeleteBinding
import com.junhyuk.dailynote.model.`object`.MemoObject
import com.junhyuk.dailynote.model.database.MemoData
import com.junhyuk.dailynote.model.repository.MemoRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/*
*
* 파일명: CheckDialog
* 역할: 정말 삭제할 것인지 묻는 Dialog
* 작성자: YangJunHyuk333
*
* */

@AndroidEntryPoint
class DeleteDialog : BottomSheetDialogFragment() {

    //binding, viewModel, viewModelFactory 선언
    private val binding by lazy { DialogDeleteBinding.inflate(layoutInflater) }
    private var memoId: Int? = 0

    //Inject
    @Inject
    lateinit var repository: MemoRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //getMemoId
        memoId = tag?.toInt()

        //view 접근
        with(binding) {

            //deleteButtonClickAction
            deleteButton.setOnClickListener {
                if(tag?.isNotEmpty() == true){
                    deleteMemo(MemoData(memoId!!, MemoObject.title,  MemoObject.content))
                    dismiss()
                }else{
                    Toast.makeText(activity, "Error", Toast.LENGTH_LONG).show()
                    dismiss()
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
        repository.deleteMemo(memo)
    }

}