package com.example.playlistmaker

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.find.FindActivity
import com.example.playlistmaker.find.TRACK_LIST_KEY
import com.example.playlistmaker.find.Track
import com.google.gson.Gson


class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val stateSave = StateSaveClass()
        val sharedSet = getSharedPreferences(SETTING_SAVE, MODE_PRIVATE)
        val cont = findViewById<ImageView>(R.id.control)
        var stateG = stateSave.readState(sharedSet)
        //Toast.makeText(this, "Код темы ${stateG}",Toast.LENGTH_LONG).show()
        if(stateG == 2){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            cont.setImageResource(R.drawable.control2)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            cont.setImageResource(R.drawable.control)
        }

        cont.setOnClickListener {
            var state:Int = -1
            if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
                cont.setImageResource(R.drawable.control2)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                state = AppCompatDelegate.getDefaultNightMode()
                stateSave.writeState(sharedSet,state)
            }else{
                cont.setImageResource(R.drawable.control)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                state = AppCompatDelegate.getDefaultNightMode()
                stateSave.writeState(sharedSet,state)
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
    private fun readState(sharedPreferences: SharedPreferences): Int { // функция выгрузки из sharedPreferences служебная
        val json = sharedPreferences.getInt(SETTING_SAVE, -1)
        return json
    }

    private fun writeState(sharedPreferences: SharedPreferences, state:Int) { // функция сохранения в sharedPreferences служебная
        sharedPreferences.edit()
            .putInt(SETTING_SAVE, state)
            .apply()
    }

}
