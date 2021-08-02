package com.junhyuk.simplememojunhyuk.view.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.junhyuk.simplememojunhyuk.R
import com.junhyuk.simplememojunhyuk.application.MyApplication
import com.junhyuk.simplememojunhyuk.databinding.ActivityPostBinding
import com.junhyuk.simplememojunhyuk.model.MemoObject
import com.junhyuk.simplememojunhyuk.viewmodel.post.PostActivityViewModel
import com.junhyuk.simplememojunhyuk.viewmodel.post.PostActivityViewModelFactory

/*
*
* 파일명: PostActivity
* 역할: PostActivity Fragment 를 띄워주기 위한 Activity
* 작성자: YangJunHyuk333
*
* */

class PostActivity : AppCompatActivity() {

    //binding, viewModel, viewModelFactory 선언
    private lateinit var binding: ActivityPostBinding
    private lateinit var viewModel: PostActivityViewModel
    private lateinit var viewModelFactory: PostActivityViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //dataBinding 설정
        binding = DataBindingUtil.setContentView(this@PostActivity, R.layout.activity_post)

        //viewModel 설정
        viewModelFactory = PostActivityViewModelFactory(application)
        viewModel = ViewModelProvider(this@PostActivity, viewModelFactory).get(PostActivityViewModel::class.java)

    }

    override fun onDestroy() {
        super.onDestroy()
        MemoObject.clear()
    }

}