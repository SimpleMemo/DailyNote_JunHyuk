package com.junhyuk.simplememojunhyuk.view.post

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.junhyuk.simplememojunhyuk.R
import com.junhyuk.simplememojunhyuk.application.MyApplication
import com.junhyuk.simplememojunhyuk.databinding.FragmentInputBinding
import com.junhyuk.simplememojunhyuk.model.`object`.MemoObject
import com.junhyuk.simplememojunhyuk.viewmodel.post.InputFragmentViewModel
import com.junhyuk.simplememojunhyuk.viewmodel.post.InputFragmentViewModelFactory

/*
*
* 파일명: InputFragment
* 역할: 메모 입력을 위한 Fragment
* 작성자: YangJunHyuk333
*
* */

class InputFragment : Fragment() {

    //binding, viewModel, viewModelFactory 선언
    private lateinit var binding: FragmentInputBinding
    private lateinit var viewModel: InputFragmentViewModel
    private lateinit var viewModelFactory: InputFragmentViewModelFactory

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //dataBinding 설정
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_input, container, false)

        //viewModel 설정
        viewModelFactory = InputFragmentViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this@InputFragment, viewModelFactory).get(
            InputFragmentViewModel::class.java)
        binding.myViewModel = viewModel

        //state, position 저장
        if(viewModel.stateData.value?.isNotEmpty() == true){
            MemoObject.state = viewModel.stateData.value.toString()
            MemoObject.position = viewModel.positionData.value!!
        }else{
            viewModel.setState(MemoObject.state)
            viewModel.setPosition(MemoObject.position)
        }

        //view 접근
        binding.apply {

            //상단 Text 를 어떤 작업을 하느냐에 따라서 변경
            when(viewModel.stateData.value){
                "UPDATE" -> textView.text = "Edit Diary"
                "INSERT" -> textView.text = "Add Diary"
            }

            //Boolean 변수 선언
            var textNullCheck: Boolean

            //해당 position 에 해당하는 제목과 내용을 EditText 에 입력(Update)
            if(MemoObject.title.isNotEmpty()){
                viewModel.apply {
                    title.value = MemoObject.title
                    content.value = MemoObject.content
                }
            }

            //Post
            postButton.setOnClickListener {

                //textNullCheck 변수 초기화
                textNullCheck =
                    inputTitle.text.toString().isNotEmpty() and inputContent.text.toString()
                        .isNotEmpty()

                //Text Null Check
                if (textNullCheck) {

                    //MemoObject 안에 title 이 존재한다면 Update
                    when (viewModel.stateData.value) {
                        "UPDATE" -> {

                            //title, content 초기화
                            MemoObject.title = viewModel.title.value.toString()
                            MemoObject.content = viewModel.content.value.toString()

                            //memoId 초기화
                            viewModel.getAllMemo().observe(requireActivity(), { list ->
                                if (viewModel.getAllMemo().value?.isNotEmpty() == true) {
                                    MemoObject.dataIndex = list[viewModel.positionData.value!!].memoId
                                }
                            })

                            //PostFragment 로 이동
                            Navigation.findNavController(it).navigate(R.id.action_inputFragment_to_postFragment)

                        }

                        //MemoObject 안에 title 이 존재하지 않는다면 Insert
                        "INSERT" -> {

                            //title, content 초기화
                            MemoObject.title = viewModel.title.value.toString()
                            MemoObject.content = viewModel.content.value.toString()

                            //PostFragment 로 이동
                            Navigation.findNavController(it).navigate(R.id.action_inputFragment_to_postFragment)

                        }

                        else -> {
                            //만약 중간에 state 값을 잃었다면
                            Toast.makeText(MyApplication.applicationContext(), "Error", Toast.LENGTH_LONG).show()
                            requireActivity().finish()
                        }
                    }

                }

                //만약 아무런 값도 입력이 안되어 있다면
                else {
                    Toast.makeText(MyApplication.applicationContext(), "Enter a title or content", Toast.LENGTH_LONG).show()
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