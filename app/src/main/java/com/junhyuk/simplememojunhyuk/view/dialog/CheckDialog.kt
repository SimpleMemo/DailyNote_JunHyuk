package com.junhyuk.simplememojunhyuk.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.junhyuk.simplememojunhyuk.R
import com.junhyuk.simplememojunhyuk.databinding.DialogCheckBinding
import com.junhyuk.simplememojunhyuk.viewmodel.dialog.CheckDialogViewModel
import com.junhyuk.simplememojunhyuk.viewmodel.dialog.CheckDialogViewModelFactory
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
    private lateinit var binding: DialogCheckBinding
    private lateinit var viewModel: CheckDialogViewModel
    private lateinit var viewModelFactory: CheckDialogViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //dataBinding 설정
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_check, container, false)

        //viewModel 설정
        viewModelFactory = CheckDialogViewModelFactory()
        viewModel = ViewModelProvider(this@CheckDialog, viewModelFactory).get(CheckDialogViewModel::class.java)

        //view 접근
        binding.apply {

            //deleteButtonClickAction
            deleteButton.setOnClickListener {

                //getMemoId
                val memoId: Int? = tag?.toInt()

                //delete
                CoroutineScope(Dispatchers.IO).launch {
                    if(tag?.isNotEmpty() == true){
                        viewModel.deleteMemo(memoId)
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

}