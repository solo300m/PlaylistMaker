package com.example.playlistmaker

import android.app.Application
import com.example.playlistmaker.AppState
import com.example.playlistmaker.NIGHT_CONST
import com.example.playlistmaker.SETTING_SAVE

class App: Application() {
    val stateSave = AppState()

    override fun onCreate() {
        super.onCreate()
        val sharedSet = getSharedPreferences(SETTING_SAVE, MODE_PRIVATE)
        var stateG = stateSave.readState(sharedSet)

        if(stateG == NIGHT_CONST){
            stateSave.setNightMode()
        }else{
            stateSave.setLightMode()
        }
    }
}
