package com.junhyuk.dailynote.view.post

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.junhyuk.dailynote.R
import com.junhyuk.dailynote.databinding.FragmentInputBinding
import com.junhyuk.dailynote.model.`object`.MemoObject
import com.junhyuk.dailynote.viewmodel.post.InputFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

/*
*
* 파일명: InputFragment
* 역할: 메모 입력을 위한 Fragment
* 작성자: YangJunHyuk333
*
* */

@AndroidEntryPoint
class InputFragment : Fragment() {

    //binding, viewModel, viewModelFactory 선언
    private val binding by lazy { FragmentInputBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<InputFragmentViewModel>()

    //Boolean 변수 선언
    private var textNullCheck: Boolean? = null
    private val isUpdate: Boolean = MemoObject.title.isNotEmpty()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //viewModel 설정
        binding.myViewModel = viewModel

        //state, position 저장
        with(viewModel){

            MemoObject.title = title.value.toString()

            if(stateData.value?.isNotEmpty() == true){
                setObject()
            }else{
                setValue(MemoObject.id, MemoObject.title, MemoObject.content, MemoObject.state)
            }

            //해당 position 에 해당하는 제목과 내용을 EditText 에 입력(Update)
            if(isUpdate){
                viewModel.setValue(MemoObject.id, MemoObject.title, MemoObject.content, MemoObject.state)
            }
        }

        //view 접근
        with(binding) {

            //상단 Text 를 어떤 작업을 하느냐에 따라서 변경
            when(viewModel.stateData.value){
                "UPDATE" -> titleText.text = "일기 수정"
                "INSERT" -> titleText.text = "일기 작성"
            }

            //Post
            nextButton.setOnClickListener {

                //textNullCheck 변수 초기화
                textNullCheck =
                    inputTitle.text.toString().isNotEmpty() and inputContent.text.toString()
                        .isNotEmpty()

                //Text Null Check
                if (textNullCheck!!) {

                    //MemoObject 안에 title 이 존재한다면 Update
                    when (viewModel.stateData.value) {
                        "UPDATE" -> {

                            //object 초기화
                            viewModel.setObject()

                            //PostFragment 로 이동
                            findNavController().navigate(R.id.action_inputFragment_to_postFragment)

                        }

                        //MemoObject 안에 title 이 존재하지 않는다면 Insert
                        "INSERT" -> {

                            //object 초기화
                            viewModel.setObject()

                            //PostFragment 로 이동
                            findNavController().navigate(R.id.action_inputFragment_to_postFragment)

                        }

                        else -> {
                            //만약 중간에 state 값을 잃었다면
                            Toast.makeText(context, "오류가 생겼습니다. 개발자에게 문의해주세요!", Toast.LENGTH_LONG).show()
                            requireActivity().finish()
                        }
                    }

                }

                //만약 아무런 값도 입력이 안되어 있다면
                else {
                    Toast.makeText(context, "제목이나 내용을 입력해주세요!", Toast.LENGTH_LONG).show()
                }

            }

            //뒤로가기
            backButton.setOnClickListener {
                requireActivity().finish() //Activity 종료
            }

        }

        return binding.root
    }

}