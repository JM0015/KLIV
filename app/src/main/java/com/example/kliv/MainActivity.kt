package com.example.kliv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kliv.databinding.ActivityMainBinding
import com.example.kliv.databinding.FragmentBoardBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = FragmentBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}