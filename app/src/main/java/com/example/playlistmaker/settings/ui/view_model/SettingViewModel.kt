package com.example.playlistmaker.settings.ui.view_model

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.main.domain.api.AppStateInterface
import com.example.playlistmaker.main.domain.impl.AppState
import com.example.playlistmaker.main.domain.impl.SETTING_SAVE
import com.example.playlistmaker.settings.domain.api.SettingInteractor
import com.example.playlistmaker.settings.domain.impl.SettingInteractorImpl
import com.example.playlistmaker.sharing.domain.api.SharingInteractor
import com.example.playlistmaker.sharing.domain.impl.SharingInteractorImpl

class SettingViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val appThis = application
    private val appState: AppStateInterface = AppState(getSharedPreferences())
    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingViewModel(
                    this[ APPLICATION_KEY] as Application
                )
            }
        }
    }
    fun getSharedPreferences(): SharedPreferences{
        return appThis.getSharedPreferences(SETTING_SAVE, MODE_PRIVATE)
    }
    fun testViewModel(str:String){
        Toast.makeText(getApplication(),str,Toast.LENGTH_LONG).show()
    }
    fun readState(): Int{
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
