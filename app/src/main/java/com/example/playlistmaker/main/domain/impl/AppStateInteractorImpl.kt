package com.example.playlistmaker.main.domain.impl

import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.main.domain.models.SharedData
import com.example.playlistmaker.main.domain.api.AppStateInteractor

class AppStateInteractorImpl(sharedPreferences: SharedData): AppStateInteractor{
    private val sharedPref = sharedPreferences
    override fun readState(): Int? { // функция выгрузки из sharedPreferences служебная
        val json = sharedPref.sharedPreferences?.getInt(sharedPref.exsseption, -1)
        return json
    }

    override fun writeState(state:Int) { // функция сохранения в sharedPreferences служебная
        sharedPref.sharedPreferences?.edit()
            ?.putInt(sharedPref.exsseption, state)
            ?.apply()
    }
    override fun setNightMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
    override fun setLightMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
