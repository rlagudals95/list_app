package com.example.chapter1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_memo.view.*

class MyAdapter(val context: Context,
                val list : List<MemoEntity>) : RecyclerView.Adapter<MyAdapter.MyViewHoler>() {

    // 리스트 사이즈
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHoler {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_memo, parent, false)

        return  MyViewHoler(itemView)
    }

    // 틀을 만든 것과 리스트안에 내용을 합쳐준다
    override fun onBindViewHolder(holder: MyViewHoler, position: Int) {
        // list = 1,2,3
        val memo = list[position]

        holder.memo.text = memo.memo
        // 꾹 ~ 누르면 delete
        holder.root.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {
                return true
            }
        })
    }

    inner class MyViewHoler (itemView : View) : RecyclerView.ViewHolder (itemView) {

        val memo = itemView.textview_memo
        val root = itemView.root
    }


}