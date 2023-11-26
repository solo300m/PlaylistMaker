package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val image = findViewById<ImageView>(R.id.poster)
        //реализация ананимного класса
        /*val imageClickListener: View.OnClickListener = object : View.OnClickListener{
            override fun onClick(v: View?) {
                Toast.makeText(this@MainActivity, "Нажали на картинку", Toast.LENGTH_LONG).show()
            }
        }
        image.setOnClickListener(imageClickListener)*/

        //лямбда выражение
        /*image.setOnClickListener{
            Toast.makeText(this@MainActivity, "Нажали на картинку", Toast.LENGTH_SHORT).show()
        }*/
        val findButton = findViewById<Button>(R.id.find)
        val mTeka = findViewById<Button>(R.id.mediateka)
        val settButton = findViewById<Button>(R.id.setting)

        findButton.setOnClickListener {
            startActivity(Intent(this, FindActivity::class.java))
        };

        mTeka.setOnClickListener {
            //val intent = Intent(this, MediatekaActivity::class.java)
            //Toast.makeText(this@MainActivity, "Нажата кнопка Медиатека!", Toast.LENGTH_SHORT ).show()
            startActivity(Intent(this, MediatekaActivity::class.java))
        }
        settButton.setOnClickListener {
            //val intent = Intent(this, SettingActivity::class.java)
            //Toast.makeText(this@MainActivity, "Нажата кнопка Настройки", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, SettingActivity::class.java))
        }
    }
}
