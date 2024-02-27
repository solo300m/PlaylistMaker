package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.find.FindActivity

import java.io.Serializable


data class Buffer(
    val text1: String,
    val text2: String
) : Serializable

class MainActivity : AppCompatActivity() {
    val stateSave = AppState()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedSet = getSharedPreferences(SETTING_SAVE, MODE_PRIVATE)
        var stateG = stateSave.readState(sharedSet)

        if(stateG == NIGHT_CONST){

            stateSave.setNightMode()
        }else{

            stateSave.setLightMode()
        }

        val findButton = findViewById<Button>(R.id.find)
        val mTeka = findViewById<Button>(R.id.mediateka)
        val settButton = findViewById<Button>(R.id.setting)

        findButton.setOnClickListener {

            val intent = Intent(this, FindActivity::class.java)

            startActivity(intent)
        };

        mTeka.setOnClickListener {

            startActivity(Intent(this, MediatekaActivity::class.java))
        }
        settButton.setOnClickListener {

            startActivity(Intent(this, SettingActivity::class.java))
        }
    }
}
