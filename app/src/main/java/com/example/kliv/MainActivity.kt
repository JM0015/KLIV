package com.example.kliv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.kliv.databinding.ActivityMainBinding
import com.example.kliv.databinding.FragmentBoardBinding
import com.example.kliv.ui_button.HomebtnActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed(Runnable {
            val i = Intent(this, HomebtnActivity::class.java)
            startActivity(i)

            finish()
        }, 3000)
    }
    }
}