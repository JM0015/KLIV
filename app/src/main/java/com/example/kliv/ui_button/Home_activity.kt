package com.example.kliv.ui_button

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import com.example.kliv.R
import com.example.kliv.ui_button.Board_activity


public class Home_activity : Board_activity() {
    override fun onCreate(savedInstantState: Bundle?){
        super.onCreate(savedInstantState)
        setContentView(R.layout.activity_home)

        val detailButton = findViewById<Button>(R.id.detail_button)
    }

    fun moveToBoardActivity(){
        val intent = Intent(this, Board_activity::class.java)
        startActivity(intent)
    }

}