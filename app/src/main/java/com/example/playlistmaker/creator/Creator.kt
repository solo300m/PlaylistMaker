package com.example.playlistmaker.creator

import android.app.Activity
import android.content.SharedPreferences
import com.example.playlistmaker.main.data.dto.SharedRepository
import com.example.playlistmaker.main.data.dto.SharedRepositoryImpl
import com.example.playlistmaker.main.domain.api.AppStateInteractor
import com.example.playlistmaker.main.domain.impl.AppStateInteractorImpl

const val SETTING_SAVE:String = "set theme"

object Creator {

    fun getSharedRepository():SharedRepository{
        return SharedRepositoryImpl()
    }
    fun getAppStateInteractor(sharedPreferences:SharedPreferences):AppStateInteractor{
        return AppStateInteractorImpl(sharedPreferences)
    }
}
