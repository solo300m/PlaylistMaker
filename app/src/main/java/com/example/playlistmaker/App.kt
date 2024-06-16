package com.example.playlistmaker

import android.app.Application
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.creator.SETTING_SAVE
import com.example.playlistmaker.settings.ui.activity.NIGHT_CONST


class App: Application() {
    override fun onCreate() {
        super.onCreate()
        val sharedSet = Creator.getSharedRepository().getSharedPreferences(this, SETTING_SAVE)
        //val sharedSet = getSharedPreferences(SETTING_SAVE, MODE_PRIVATE)
        val stateSave = Creator.getAppStateInteractor(sharedSet)
        var stateG = stateSave.readState()

        if(stateG == NIGHT_CONST){
            stateSave.setNightMode()
        }else{
            stateSave.setLightMode()
        }
    }
}
