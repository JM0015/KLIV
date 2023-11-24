package com.example.kliv.ui_button

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.kliv.MainActivity
import com.example.kliv.R

class Loading_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            val i = Intent(this,
                MainActivity::class.java)
            startActivity(i)
            finish()
        }, 1000)
    }
}