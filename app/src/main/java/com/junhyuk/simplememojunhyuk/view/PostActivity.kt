package com.junhyuk.simplememojunhyuk.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.junhyuk.simplememojunhyuk.R
import com.junhyuk.simplememojunhyuk.databinding.ActivityPostBinding
import com.junhyuk.simplememojunhyuk.model.MemoData
import com.junhyuk.simplememojunhyuk.model.MemoObject
import com.junhyuk.simplememojunhyuk.viewmodel.PostActivityViewModel
import com.junhyuk.simplememojunhyuk.viewmodel.PostActivityViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
*
* 파일명: PostActivity
* 역할: Post(Insert, Update) 처리
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
        binding.myViewModel = viewModel

        //update 에 필요한 변수 설정
        val position = MemoObject.position
        val updateTitle = MemoObject.title
        val updateContent = MemoObject.content
        var memoId = 0
        
        //memoId 초기화
        viewModel.getAllMemo().observe(this@PostActivity, {
            if (viewModel.getAllMemo().value?.isNotEmpty() == true) {
                memoId = viewModel.getAllMemo().value?.get(position)?.memoId!!
            }
        })

        //view 접근
        binding.apply {

            //Boolean 변수 선언
            var textNullCheck: Boolean
            val objectNullCheck = MemoObject.title != ""

            //해당 position 에 해당하는 제목과 내용을 EditText 에 입력(Update)
            viewModel.apply {
                title.value = updateTitle
                content.value = updateContent
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
                    if (objectNullCheck) {
                        CoroutineScope(Dispatchers.IO).launch {
                            viewModel.update(memoId, viewModel.title.value.toString(), viewModel.content.value.toString())
                        }
                    }

                    //MemoObject 안에 title 이 존재하지 않는다면 Insert
                    else {
                        CoroutineScope(Dispatchers.IO).launch {
                            viewModel.insert(MemoData(inputTitle.text.toString(), inputContent.text.toString()))
                        }
                    }

                    //해당 작업을 끝낸 후에는 MemoObject 의 값을 초기화
                    MemoObject.clear()
                    finish()

                }

                //만약 아무런 값도 입력이 안되어 있다면
                else {
                    Toast.makeText(this@PostActivity, "Enter a title or content", Toast.LENGTH_LONG).show()
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