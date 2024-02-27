package com.example.playlistmaker

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

const val SETTING_SAVE:String = "set theme"

class AppState {
    fun readState(sharedPreferences: SharedPreferences): Int { // функция выгрузки из sharedPreferences служебная
        val json = sharedPreferences.getInt(SETTING_SAVE, -1)
        return json
    }

    fun writeState(sharedPreferences: SharedPreferences, state:Int) { // функция сохранения в sharedPreferences служебная
        sharedPreferences.edit()
            .putInt(SETTING_SAVE, state)
            .apply()
    }
    fun setNightMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
    fun setLightMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
