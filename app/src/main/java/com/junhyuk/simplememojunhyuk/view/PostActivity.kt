package com.junhyuk.simplememojunhyuk.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.junhyuk.simplememojunhyuk.R
import com.junhyuk.simplememojunhyuk.databinding.ActivityPostBinding
import com.junhyuk.simplememojunhyuk.model.MemoData
import com.junhyuk.simplememojunhyuk.model.MemoObject
import com.junhyuk.simplememojunhyuk.viewmodel.PostActivityViewModel
import com.junhyuk.simplememojunhyuk.viewmodel.PostActivityViewModelFactory

class PostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostBinding
    private lateinit var viewModel: PostActivityViewModel
    private lateinit var viewModelFactory: PostActivityViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        //dataBinding 설정
        binding = DataBindingUtil.setContentView(
            this@PostActivity,
            R.layout.activity_post
        )

        //viewModel 설정
        viewModelFactory = PostActivityViewModelFactory(application)
        viewModel = ViewModelProvider(
            this@PostActivity,
            viewModelFactory
        ).get(PostActivityViewModel::class.java)

        //update 에 필요한 변수 설정
        val position = MemoObject.position
        val title = MemoObject.title
        val content = MemoObject.content

        //view 접근
        binding.apply {

            //해당 position 에 해당하는 제목과 내용을 EditText 에 입력
            inputTitle.setText(title)
            inputContent.setText(content)

            //Post
            postButton.setOnClickListener {
                //Text Null Check
                if (inputTitle.text.toString().isNotEmpty()
                    and inputContent.text.toString().isNotEmpty()
                ) {
                    //MemoObject 안에 title 이 존재한다면 Update
                    if (MemoObject.title != "") {

                        viewModel.update(((position + 1).toInt()), inputTitle.text.toString(), inputContent.text.toString())

                    } else {

                        //MemoObject 안에 title 이 존재하지 않는다면 Insert
                        val memoData = MemoData(inputTitle.text.toString(), inputContent.text.toString())
                        viewModel.insert(memoData)

                    }

                    //해당 작업을 끝낸 후에는 MemoObject 의 값을 초기화
                    MemoObject.clear()
                    finish()
                    
                } else {
                    //만약 아무런 값도 입력이 안되어 있다면
                    Toast.makeText(this@PostActivity, "Enter a title or content!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    //뒤로가기 처리
    override fun onBackPressed() {
        super.onBackPressed()
        MemoObject.clear()
    }
}