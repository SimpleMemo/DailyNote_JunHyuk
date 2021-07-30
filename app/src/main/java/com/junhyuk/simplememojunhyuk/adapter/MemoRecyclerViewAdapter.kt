package com.junhyuk.simplememojunhyuk.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.junhyuk.simplememojunhyuk.R
import com.junhyuk.simplememojunhyuk.model.MemoData
import com.junhyuk.simplememojunhyuk.model.MemoObject
import com.junhyuk.simplememojunhyuk.view.PostActivity

/*
*
* 파일명: MemoRecyclerViewAdapter
* 역할: RecyclerView 의 Adapter 로 RecyclerView 의 Data, Action 등을 관리
* 작성자: YangJunHyuk333
*
* */

class MemoRecyclerViewAdapter(private val memoList: List<MemoData>, private val context: Context) : RecyclerView.Adapter<MemoRecyclerViewAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_memo, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (memoList.isNotEmpty()) {
            val currentMemoData: MemoData = memoList[position]
            holder.memoTitle.text = currentMemoData.memoTitle
            holder.memoContent.text = currentMemoData.memoContent
        }else{
            holder.memoTitle.text = "메모가 없음"
            holder.memoContent.text = "메모가 없음"
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, PostActivity::class.java)
            MemoObject.position = position.toLong()
            MemoObject.title = holder.memoTitle.text.toString()
            MemoObject.content = holder.memoContent.text.toString()
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return if(memoList.isNotEmpty()){
            memoList.size
        }else 0
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var memoTitle: TextView = itemView.findViewById(R.id.title)
        var memoContent: TextView = itemView.findViewById(R.id.content)
    }

}