package com.junhyuk.simplememojunhyuk.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.junhyuk.simplememojunhyuk.R
import com.junhyuk.simplememojunhyuk.adapter.MemoRecyclerViewAdapter
import com.junhyuk.simplememojunhyuk.databinding.ActivityMainBinding
import com.junhyuk.simplememojunhyuk.viewmodel.MainActivityViewModel
import com.junhyuk.simplememojunhyuk.viewmodel.MainActivityViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
*
* 파일명: MainActivity
* 역할: MainActivity 메모를 RecyclerView 를 통해 띄워줌
* 작성자: YangJunHyuk333
*
* */

class MainActivity : AppCompatActivity() {

    //binding, viewModel, viewModelFactory 선언
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var viewModelFactory: MainActivityViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //dataBinding 설정
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)

        //viewModel 설정
        viewModelFactory = MainActivityViewModelFactory(application)
        viewModel = ViewModelProvider(this@MainActivity, viewModelFactory).get(MainActivityViewModel::class.java)
        binding.myViewModel = viewModel

        //SwipeAction
        val itemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            //swipe action
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                CoroutineScope(Dispatchers.IO).launch {
                    //삭제할 것인지 묻는 Dialog
                    val checkDialog = CheckDialog()
                    checkDialog.show(supportFragmentManager, viewModel.getAllMemo().value?.get(viewHolder.adapterPosition)?.memoId.toString())
                    binding.myAdapter!!.notifyItemChanged(viewHolder.adapterPosition)
                }
            }

        }

        //view 접근
        binding.apply {

            //recyclerView 가 고정된 사이즈를 가진다고 알려주는 함수
            memoRecyclerView.setHasFixedSize(true)

            //메모 DB 에서 메모 Data 를 불러와서 recyclerview 에 적용
            viewModel.getAllMemo().observe(this@MainActivity, {
                myAdapter = MemoRecyclerViewAdapter(it, this@MainActivity)
                val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
                itemTouchHelper.attachToRecyclerView(memoRecyclerView)
            })

            //메모를 추가하는 PostActivity 로 이동
            addButton.setOnClickListener {

                val intent = Intent(this@MainActivity, PostActivity::class.java)
                startActivity(intent)

            }

        }

    }

}
