package com.example.playlistmaker.main.ui.view_models

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.main.domain.impl.SETTING_SAVE


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences = application.getSharedPreferences(SETTING_SAVE, MODE_PRIVATE)
    companion object {
        fun getViewModelFactory() : ViewModelProvider.Factory = viewModelFactory{
            initializer {
                MainViewModel(this[APPLICATION_KEY] as Application)
            }
        }
    }

    fun testViewModel(str:String){
        Toast.makeText(this.getApplication(), str, Toast.LENGTH_LONG).show()
    }
}
