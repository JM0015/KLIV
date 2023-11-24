package com.example.kliv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kliv.adapter.BoardAdapter
import com.example.kliv.databinding.ActivityBoardBinding
import com.example.kliv.databinding.ActivityMainBinding
import com.example.kliv.databinding.FragmentBoardBinding
import com.example.kliv.dto.BoardTitle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)

        activityBinding.mapBtn.setOnClickListener {
            val mapIntent = Intent(this, MapActivity::class.java)
            startActivity(mapIntent)
        }

//        activityBinding.boardBtn.setOnClickListener {
//            val boardIntent = Intent(this, BoardActivity::class.java)
//            startActivity(boardIntent)
//        }

        val listData = mutableListOf<BoardTitle>()
        for (i in 1..10) {
            listData.add(BoardTitle("${i}", "title ${i}"))
        }

        activityBinding.boardRecyclerView.layoutManager = LinearLayoutManager(this)
        activityBinding.boardRecyclerView.adapter = BoardAdapter(listData)

    }
}