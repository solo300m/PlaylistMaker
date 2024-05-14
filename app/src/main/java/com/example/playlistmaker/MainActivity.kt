package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.playlistmaker.find.FindActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val findButton = findViewById<Button>(R.id.find)
        val mediatekaButton = findViewById<Button>(R.id.mediateka)
        val settButton = findViewById<Button>(R.id.setting)

        findButton.setOnClickListener {

            val intent = Intent(this, FindActivity::class.java)

            startActivity(intent)
        };

        mediatekaButton.setOnClickListener {

            startActivity(Intent(this, MediatekaActivity::class.java))
        }
        settButton.setOnClickListener {

            startActivity(Intent(this, SettingActivity::class.java))
        }
    }
}
