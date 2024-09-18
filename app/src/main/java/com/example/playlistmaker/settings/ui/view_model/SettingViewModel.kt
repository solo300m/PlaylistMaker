package com.example.playlistmaker.settings.ui.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.main.domain.api.AppStateInteractor


class SettingViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val appState: AppStateInteractor = Creator.getAppStateInteractor(application)
    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingViewModel(
                    this[ APPLICATION_KEY] as Application
                )
            }
        }
    }
    /*fun getSharedPreferences(): SharedPreferences{
        return appThis.getSharedPreferences(SETTING_SAVE, MODE_PRIVATE)
    }
    fun testViewModel(str:String){
        Toast.makeText(getApplication(),str,Toast.LENGTH_LONG).show()
    }*/
    fun readState(): Int?{
        return appState.readState()
    }
    fun writeState(state:Int){
        appState.writeState(state)
    }
    fun setNightMode(){
        appState.setNightMode()
    }
    fun setLightMode(){
        appState.setLightMode()
    }
}
