package com.example.kliv.ui_button

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.kliv.R

class Home_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val detailButton =findViewById<ImageButton>(R.id.detail_button);

        // 버튼 클릭 이벤트 처리
        detailButton.setOnClickListener {
            moveToBoardActivity()
        }
    }

    fun moveToBoardActivity() {
        val intent = Intent(this, Board_activity::class.java)
        startActivity(intent)
    }
}
