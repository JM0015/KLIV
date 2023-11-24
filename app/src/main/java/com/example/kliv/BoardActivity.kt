package com.example.kliv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kliv.adapter.BoardAdapter
import com.example.kliv.databinding.ActivityBoardBinding
import com.example.kliv.dto.BoardTitle

class BoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listData = mutableListOf<BoardTitle>()
        for (i in 1..10) {
            listData.add(BoardTitle("${i}", "title ${i}"))
        }

        binding.boardRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.boardRecyclerView.adapter = BoardAdapter(listData)


    }
}