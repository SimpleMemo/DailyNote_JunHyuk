package com.junhyuk.simplememojunhyuk.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.junhyuk.simplememojunhyuk.R
import com.junhyuk.simplememojunhyuk.adapter.MemoRecyclerViewAdapter
import com.junhyuk.simplememojunhyuk.databinding.ActivityMainBinding
import com.junhyuk.simplememojunhyuk.viewmodel.MainActivityViewModel
import com.junhyuk.simplememojunhyuk.viewmodel.MainActivityViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var viewModelFactory: MainActivityViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        viewModelFactory = MainActivityViewModelFactory(application)
        viewModel = ViewModelProvider(
            this@MainActivity,
            viewModelFactory
        ).get(MainActivityViewModel::class.java)

        Log.d("memoSize", "data: ${viewModel.getAllMemo().value?.get(0)?.memoTitle}")

        binding.apply {
            memoRecyclerView.setHasFixedSize(true)

            viewModel.getAllMemo().observe(this@MainActivity, {
                val memoAdapter = MemoRecyclerViewAdapter(it, this@MainActivity)
                memoRecyclerView.adapter = memoAdapter
                memoRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            })

            addButton.setOnClickListener {
                val intent = Intent(this@MainActivity, PostActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
