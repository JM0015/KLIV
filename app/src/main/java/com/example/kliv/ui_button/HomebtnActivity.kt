package com.example.kliv.ui_button

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.kliv.R

class HomebtnActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val detailButton =findViewById<ImageButton>(R.id.detail_button);
        val locationButton = findViewById<ImageButton>(R.id.location_button);
        // 버튼 클릭 이벤트 처리
        detailButton.setOnClickListener {
            moveToBoardbtnActivity()
        }

        locationButton.setOnClickListener{
            moveToLoginbtnActivity()
        }
    }

    fun moveToLoginbtnActivity() {
        val intent = Intent(this,LocationbtnActivity::class.java)
        startActivity(intent)
    }

    fun moveToBoardbtnActivity() {
        val intent = Intent(this, BoardbtnActivity::class.java)
        startActivity(intent)
    }
}

