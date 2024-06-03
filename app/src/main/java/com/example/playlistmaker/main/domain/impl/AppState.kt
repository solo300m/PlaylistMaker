package com.example.playlistmaker.main.domain.impl

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.main.domain.api.AppStateInterface

const val SETTING_SAVE:String = "set theme"

class AppState (sharedPreferences: SharedPreferences): AppStateInterface{
    private val sharedPref = sharedPreferences
    override fun readState(): Int { // функция выгрузки из sharedPreferences служебная
        val json = sharedPref.getInt(SETTING_SAVE, -1)
        return json
    }

    override fun writeState(state:Int) { // функция сохранения в sharedPreferences служебная
        sharedPref.edit()
            .putInt(SETTING_SAVE, state)
            .apply()
    }
    override fun setNightMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
    override fun setLightMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
