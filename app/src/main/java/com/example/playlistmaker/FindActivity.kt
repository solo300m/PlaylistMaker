package com.example.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class FindActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find)

        val findButton = findViewById<Button>(R.id.backF)
        findButton.setOnClickListener {
            val intent4 = Intent(this, MainActivity::class.java)
            startActivity(intent4)
        }
    }
}
