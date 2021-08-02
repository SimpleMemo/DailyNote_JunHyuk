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
import com.junhyuk.simplememojunhyuk.R
import com.junhyuk.simplememojunhyuk.application.MyApplication
import com.junhyuk.simplememojunhyuk.databinding.FragmentPostBinding
import com.junhyuk.simplememojunhyuk.model.database.MemoData
import com.junhyuk.simplememojunhyuk.model.MemoObject
import com.junhyuk.simplememojunhyuk.viewmodel.post.PostFragmentViewModel
import com.junhyuk.simplememojunhyuk.viewmodel.post.PostFragmentViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
*
* 파일명: PostFragment
* 역할: 메모에 입력된 정보를 DB에 저장하는 Fragment
* 작성자: YangJunHyuk333
*
* */

class PostFragment : Fragment() {

    //binding, viewModel, viewModelFactory 선언
    private lateinit var binding: FragmentPostBinding
    private lateinit var viewModel: PostFragmentViewModel
    private lateinit var viewModelFactory: PostFragmentViewModelFactory

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //dataBinding 설정
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false)

        //viewModel 설정
        viewModelFactory = PostFragmentViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this@PostFragment, viewModelFactory).get(PostFragmentViewModel::class.java)
        binding.myViewModel = viewModel

        //state 저장
        if(viewModel.stateData.value?.isNotEmpty() == true){
            MemoObject.state = viewModel.stateData.value.toString()
        }else{
            viewModel.setState(MemoObject.state)
        }

        //view 접근
        binding.apply {

            //상단 Text 를 어떤 작업을 하느냐에 따라서 변경
            when(viewModel.stateData.value){
                "UPDATE" -> textView.text = "Update Diary"
                "INSERT" -> textView.text = "Post Diary"
            }

            //해당 position 에 해당하는 제목과 내용을 EditText 에 입력(Update)
            viewModel.apply {
                if(MemoObject.title.isNotEmpty()) {
                    setTextValue(MemoObject.title, MemoObject.content)
                }else{
                    MemoObject.title = titleData.value.toString()
                    MemoObject.content = contentData.value.toString()
                }
            }

            //Post
            postButton.setOnClickListener {

                //MemoObject.state == UPDATE
                when (viewModel.stateData.value) {
                    "UPDATE" -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            viewModel.update(MemoObject.position, MemoObject.title, MemoObject.content)
                        }
                    }

                    //MemoObject.state == INSERT
                    "INSERT" -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            viewModel.insert(MemoData(MemoObject.title, MemoObject.content))
                        }
                    }

                    //만약 아무런 값도 입력이 안되어 있다면
                    else -> {
                        Toast.makeText(MyApplication.applicationContext(), "Error", Toast.LENGTH_LONG).show()
                        requireActivity().finish()
                    }
                }

                requireActivity().finish() //Activity 종료

            }

            //뒤로가기
            backButton.setOnClickListener {
                requireActivity().onBackPressed()
            }

        }

        return binding.root
    }

}