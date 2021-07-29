package com.junhyuk.simplememojunhyuk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junhyuk.simplememojunhyuk.R

class MemoRecyclerViewAdapter(private var memoArrayList: ArrayList<Int>) : RecyclerView.Adapter<MemoRecyclerViewAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_memo, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

    }

    override fun getItemCount(): Int {
        return memoArrayList.size
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
}