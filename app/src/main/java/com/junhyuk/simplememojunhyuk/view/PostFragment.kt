package com.junhyuk.simplememojunhyuk.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.junhyuk.simplememojunhyuk.R
import com.junhyuk.simplememojunhyuk.databinding.FragmentPostBinding
import com.junhyuk.simplememojunhyuk.model.MemoData
import com.junhyuk.simplememojunhyuk.model.MemoObject
import com.junhyuk.simplememojunhyuk.viewmodel.PostFragmentViewModel
import com.junhyuk.simplememojunhyuk.viewmodel.PostFragmentViewModelFactory
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

        //view 접근
        binding.apply {

            //해당 position 에 해당하는 제목과 내용을 EditText 에 입력(Update)
            viewModel.apply {
                setTextValue(MemoObject.title, MemoObject.content)
            }

            //Post
            postButton.setOnClickListener {

                //MemoObject.state == UPDATE
                when (MemoObject.state) {
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
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
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