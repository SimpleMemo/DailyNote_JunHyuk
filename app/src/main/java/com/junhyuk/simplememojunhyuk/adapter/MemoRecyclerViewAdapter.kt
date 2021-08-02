package com.junhyuk.simplememojunhyuk.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.junhyuk.simplememojunhyuk.R
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

class MemoRecyclerViewAdapter(private val memoList: List<MemoData>, private val context: Context) : RecyclerView.Adapter<MemoRecyclerViewAdapter.Holder>() {

    //viewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_memo, parent, false)
        return Holder(view)
    }

    //viewHolder 의 데이터를 바인딩
    override fun onBindViewHolder(holder: Holder, position: Int) {

        //메모 List 적용
        if (memoList.isNotEmpty()) {
            val currentMemoData: MemoData = memoList[position]
            holder.memoTitle.text = currentMemoData.memoTitle
            holder.memoContent.text = currentMemoData.memoContent
        }else{
            holder.memoTitle.text = "메모가 없음"
            holder.memoContent.text = "메모가 없음"
        }

        //메모 클릭시
        holder.itemView.setOnClickListener {
            val intent = Intent(context, PostActivity::class.java)
            MemoObject.setAll(holder.memoTitle.text.toString(), holder.memoContent.text.toString(), position, 0, "UPDATE") //MemoObject Instance 전체 설정
            context.startActivity(intent) //PostActivity 로 이동
        }
        
    }

    //RecyclerView 의 아이템 갯수를 return
    override fun getItemCount(): Int {
        return if(memoList.isNotEmpty()){
            memoList.size
        }else 0
    }

    //view 객체 선언
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var memoTitle: TextView = itemView.findViewById(R.id.title)
        var memoContent: TextView = itemView.findViewById(R.id.content)
    }

}