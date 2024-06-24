package com.example.playlistmaker

import android.app.Application
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.settings.ui.activity.NIGHT_CONST


class App: Application() {
    override fun onCreate() {
        super.onCreate()

        val stateSave = Creator.getAppStateInteractor(this)
        var stateG = stateSave.readState()

        if(stateG == NIGHT_CONST){
            stateSave.setNightMode()
        }else{
            stateSave.setLightMode()
        }
    }
}
