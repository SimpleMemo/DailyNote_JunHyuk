package com.junhyuk.simplememojunhyuk.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.junhyuk.simplememojunhyuk.R
import com.junhyuk.simplememojunhyuk.databinding.ActivityPostBinding
import com.junhyuk.simplememojunhyuk.model.MemoData
import com.junhyuk.simplememojunhyuk.viewmodel.PostActivityViewModel
import com.junhyuk.simplememojunhyuk.viewmodel.PostActivityViewModelFactory

class PostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostBinding
    private lateinit var viewModel: PostActivityViewModel
    private lateinit var viewModelFactory: PostActivityViewModelFactory
    private lateinit var getResultText: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@PostActivity, R.layout.activity_post)
        viewModelFactory = PostActivityViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(PostActivityViewModel::class.java)

        val position = intent.getIntExtra("recyclerPosition", 0)
        val title = intent.getStringExtra("recyclerTitle")
        val content = intent.getStringExtra("recyclerContent")

        val intent = Intent()

        binding.apply {
            postButton.setOnClickListener {
                if (title?.isNotEmpty() == true) {

                    inputTitle.setText(title)
                    inputContent.setText(content)

                } else {
                    if (TextUtils.isEmpty(inputTitle.text) or TextUtils.isEmpty(inputContent.text)) {
                        Toast.makeText(this@PostActivity, "제목이나 내용을 입력해주세요.", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        intent.apply {
                            putExtra("title", inputTitle.text.toString())
                            putExtra("content", inputContent.text.toString())
                            Log.d("DB_Data", "data1: ${inputTitle.text}")
                            Log.d("DB_Data", "data1: ${inputContent.text}")
                            setResult(RESULT_OK, intent)
                        }

                        if (!isFinishing) {
                            finish()
                        }
                    }
                }
            }
        }

    }
}