package com.junhyuk.simplememojunhyuk.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.junhyuk.simplememojunhyuk.R
import com.junhyuk.simplememojunhyuk.databinding.ItemMemoBinding
import com.junhyuk.simplememojunhyuk.model.database.MemoData
import com.junhyuk.simplememojunhyuk.model.`object`.MemoObject
import com.junhyuk.simplememojunhyuk.view.post.PostActivity

/*
*
* 파일명: MemoRecyclerViewAdapter
* 역할: RecyclerView 의 Adapter 로 RecyclerView 의 Data, Action 등을 관리
* 작성자: YangJunHyuk333
*
* */

class MemoRecyclerViewAdapter(private val context: Context) :
    PagingDataAdapter<MemoData, MemoRecyclerViewAdapter.Holder>(MEMO_DIFF) {

    //view 객체 선언
    inner class Holder(private val binding: ItemMemoBinding) : RecyclerView.ViewHolder(binding.root) {

        private var positionData: Int = 0

        //binding
        fun bind(item: MemoData, position: Int){
            with(binding){
                entity = item
                executePendingBindings()

                positionData = position

                //메모 클릭시
                itemView.setOnClickListener {
                    val intent = Intent(context, PostActivity::class.java)
                    MemoObject.setAll(title.text.toString(), content.text.toString(), position, 0, "UPDATE") //MemoObject Instance 전체 설정
                    context.startActivity(intent) //PostActivity 로 이동
                }
            }
        }

    }

    //viewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_memo,
                parent,
                false
            )
        )
    }

    //viewHolder 의 데이터를 바인딩
    override fun onBindViewHolder(holder: Holder, position: Int) {

        getItem(position)?.let { holder.bind(it, position) }

    }

    companion object {
        private val MEMO_DIFF = object: DiffUtil.ItemCallback<MemoData>() {
            override fun areItemsTheSame(oldItem: MemoData, newItem: MemoData): Boolean {
                return oldItem.memoId == newItem.memoId
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: MemoData, newItem: MemoData): Boolean {
                return oldItem == newItem
            }
        }
    }

}