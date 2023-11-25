package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MediatekaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        val backHome = findViewById<Button>(R.id.backM)
        backHome.setOnClickListener {
            val intent3 = Intent(this, MainActivity::class.java)
            startActivity(intent3)
        }
    }
}
