package com.junhyuk.dailynote.view.developer_info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.junhyuk.dailynote.R
import com.junhyuk.dailynote.databinding.ActivityDeveloperInfoBinding

class DeveloperInfoActivity : AppCompatActivity() {

    //binding 선언
    private val binding by lazy { ActivityDeveloperInfoBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding){
            backButton.setOnClickListener {
                finish()
            }
        }

    }
}