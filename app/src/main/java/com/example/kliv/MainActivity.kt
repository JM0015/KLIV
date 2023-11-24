package com.example.kliv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kliv.databinding.ActivityMainBinding
import com.example.kliv.databinding.FragmentBoardBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val activityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)

        activityBinding.mapBtn.setOnClickListener {
            val mapIntent = Intent(this, MapActivity::class.java)
            startActivity(mapIntent)
        }

        activityBinding.boardBtn.setOnClickListener {
            val boardIntent = Intent(this, BoardActivity::class.java)
            startActivity(boardIntent)
        }

        val binding = FragmentBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startActivity(Intent(this, BoardActivity::class.java))
    }
}