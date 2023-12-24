package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.find.FindActivity


class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val control = findViewById<ImageView>(R.id.control)
        control.setOnClickListener {
            if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
                control.setImageResource(R.drawable.control2)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            }else{
                control.setImageResource(R.drawable.control)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }
        }

        val findBut = findViewById<Button>(R.id.findComeButton)
        findBut.setOnClickListener {
            startActivity(Intent(this, FindActivity::class.java))
        }

        val mediaBut = findViewById<Button>(R.id.mediaComeButton)
        mediaBut.setOnClickListener {
            startActivity(Intent(this, MediatekaActivity::class.java))
        }

        val setting = findViewById<Button>(R.id.setComeButton)
        setting.setTextColor(resources.getColor(R.color.blue_yp, null))


        val homeButton = findViewById<ImageView>(R.id.backSettingToMain)
        homeButton.setOnClickListener {
            /*val intent2 = Intent(this, MainActivity::class.java)
            startActivity(intent2)*/
            finish()
        }
        val sareButton = findViewById<ImageView>(R.id.sareButton);
        sareButton.setOnClickListener {
            val message = resources.getString(R.string.promoText)
            val sareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,message)
                type="text/plain"
            }
            startActivity(sareIntent)
        }
        val supButton = findViewById<ImageView>(R.id.supportButton)
        supButton.setOnClickListener {

            val title:String = resources.getString(R.string.titleEmailTest)
            val substr:String = resources.getString(R.string.subjectTest)
            Intent(Intent.ACTION_SEND).apply {
                type="text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("solo300m@yandex.ru"))
                putExtra(Intent.EXTRA_SUBJECT, title)
                putExtra(Intent.EXTRA_TEXT, substr)
                startActivity(this)
            }

        }
        val termsButton = findViewById<ImageView>(R.id.termsButton)
        termsButton.setOnClickListener {
            /*val intent:Intent = Uri.parse("https://yandex.ru/legal/practicum_offer/").let{
                webpage -> Intent(Intent.ACTION_VIEW, webpage)
            }
           startActivity(intent)*/
            Intent(Intent.ACTION_VIEW).apply {
                Uri.parse(resources.getString(R.string.yandexText))
                startActivity(this)
            }
        }
    }

}
