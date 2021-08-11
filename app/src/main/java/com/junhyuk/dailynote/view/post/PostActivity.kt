package com.junhyuk.dailynote.view.post

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.junhyuk.dailynote.databinding.ActivityPostBinding
import com.junhyuk.dailynote.model.`object`.MemoObject

/*
*
* 파일명: PostActivity
* 역할: PostActivity Fragment 를 띄워주기 위한 Activity
* 작성자: YangJunHyuk333
*
* */

class PostActivity : AppCompatActivity() {

    private val binding by lazy { ActivityPostBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()

        //엑티비티 종료시 MemoObject Clear
        MemoObject.clear()
    }

}