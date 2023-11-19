package com.example.kliv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kliv.databinding.ItemMainBinding
import com.example.kliv.dto.BoardTitle

class BoardAdapter (val dataList: MutableList<BoardTitle>) : RecyclerView.Adapter<BoardAdapter.Holder>() {

    class Holder(val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root) {
        // 3. 받은 데이터를 화면에 출력한다.
        fun setTry(boardTitle: BoardTitle) {
            with(binding){
                no.text = boardTitle.no
                titleBoard.text = boardTitle.titles
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        // 1. 사용할 데이터를 꺼내고
        val boardTitle = dataList[position]
        // 2. 홀더에 데이터를 전달
        holder.setTry(boardTitle)
    }

    override fun getItemCount() = dataList.size
}