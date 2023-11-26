package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val homeButton = findViewById<ImageView>(R.id.backSettingToMain)
        homeButton.setOnClickListener {
            val intent2 = Intent(this, MainActivity::class.java)
            startActivity(intent2)
        }
    }

}
