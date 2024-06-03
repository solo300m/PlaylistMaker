package com.example.playlistmaker.main.ui.activity

import android.app.Application
import com.example.playlistmaker.main.domain.api.AppStateInterface
import com.example.playlistmaker.main.domain.impl.AppState
import com.example.playlistmaker.main.domain.impl.SETTING_SAVE
import com.example.playlistmaker.settings.ui.activity.NIGHT_CONST


class App: Application() {

    override fun onCreate() {
        super.onCreate()
        val sharedSet = getSharedPreferences(SETTING_SAVE, MODE_PRIVATE)
        val stateSave:AppStateInterface = AppState(sharedSet)
        var stateG = stateSave.readState()

        if(stateG == NIGHT_CONST){
            stateSave.setNightMode()
        }else{
            stateSave.setLightMode()
        }
    }
}
