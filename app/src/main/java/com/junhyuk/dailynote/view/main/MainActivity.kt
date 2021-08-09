package com.junhyuk.dailynote.view.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.junhyuk.dailynote.R
import com.junhyuk.dailynote.databinding.ActivityMainBinding
import com.junhyuk.dailynote.adapter.MemoRecyclerViewAdapter
import com.junhyuk.dailynote.model.`object`.MemoObject
import com.junhyuk.dailynote.view.dialog.CheckDialog
import com.junhyuk.dailynote.view.post.PostActivity
import com.junhyuk.dailynote.view.setting.SettingActivity
import com.junhyuk.dailynote.viewmodel.main.MainActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/*
*
* 파일명: MainActivity
* 역할: MainActivity 메모를 RecyclerView 를 통해 띄워줌
* 작성자: YangJunHyuk333
*
* */

class MainActivity : AppCompatActivity() {

    //binding, viewModel, viewModelFactory, adapter 선언
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: MemoRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //dataBinding 설정
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)

        //viewModel 설정
        viewModel = ViewModelProvider(this@MainActivity).get(MainActivityViewModel::class.java)
        binding.myViewModel = viewModel

        //adapter 초기화
        initAdapter()

        //권한 허용
        checkSelfPermission()

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
                    checkDialog.show(supportFragmentManager, viewModel.getAllMemo().value?.get(viewHolder.absoluteAdapterPosition)?.memoId.toString())
                    binding.myAdapter!!.notifyItemChanged(viewHolder.absoluteAdapterPosition)
                }
            }

        }

        //view 접근
        binding.apply {

            //recyclerView 가 고정된 사이즈를 가진다고 알려주는 함수
            memoRecyclerView.setHasFixedSize(true)

            //itemTouchHelper 초기화
            val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
            itemTouchHelper.attachToRecyclerView(memoRecyclerView)

            //메모 DB 에서 메모 Data 를 불러와서 recyclerview 에 적용
            viewModel.getAllMemo().observe(this@MainActivity, {

                adapter.refresh()
                isNoneTextVisible = it.isEmpty()

            })

            //메모를 추가하는 PostActivity 로 이동
            addButton.setOnClickListener {

                MemoObject.state = "INSERT"
                val intent = Intent(this@MainActivity, PostActivity::class.java)
                startActivity(intent)

            }

            //설정을 하는 SettingActivity 로 이동
            settingText.setOnClickListener {

                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)

            }

            //refresh
            refreshLayout.setOnRefreshListener {
                adapter.refresh()
                refreshLayout.isRefreshing = false
            }

        }

    }

    //ViewModel 에서 전달 받은 데이터를 Adapter 로 전달
    private fun initMemoJob(){
        lifecycleScope.launch {
            viewModel.getContent().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    //Adapter 초기화
    private fun initAdapter() {
        adapter = MemoRecyclerViewAdapter(this@MainActivity)
        binding.myAdapter = adapter
        initMemoJob()
    }

    //권한 허용
    private fun checkSelfPermission() {

        var temp = ""

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " "
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " "
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " "
        }

        if (!TextUtils.isEmpty(temp)) {
            ActivityCompat.requestPermissions(this, temp.trim().split(" ").toTypedArray(), 1)
        }

    }

}
