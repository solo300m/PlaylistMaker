package com.example.playlistmaker.creator

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.playlistmaker.main.data.dto.SharedRepository
import com.example.playlistmaker.main.data.dto.SharedRepositoryImpl
import com.example.playlistmaker.main.domain.api.AppStateInteractor
import com.example.playlistmaker.main.domain.impl.AppStateInteractorImpl
import com.example.playlistmaker.player.domain.api.PlayerRepository
import com.example.playlistmaker.player.domain.impl.PlayerRepositoryImpl
import com.example.playlistmaker.player.domain.api.MediaPlayerInteractor
import com.example.playlistmaker.player.domain.impl.MediaPlayerInteractorImpl
import com.example.playlistmaker.search.domain.api.NetworkClient
import com.example.playlistmaker.search.domain.api.SaveListRepository
import com.example.playlistmaker.search.domain.api.SearchInteractor
import com.example.playlistmaker.search.domain.api.SharedPreferencesRepository
import com.example.playlistmaker.search.domain.impl.RetrofitNetworkClient
import com.example.playlistmaker.search.domain.impl.SaveListRepositoryImpl
import com.example.playlistmaker.search.domain.impl.SearchInteractorImpl
import com.example.playlistmaker.search.domain.impl.SharedPreferencesRepositoryImpl

const val SETTING_SAVE:String = "set theme"
//val application = APPLICATION_KEY

object Creator {

    fun getSharedRepository():SharedRepository{
        return SharedRepositoryImpl()
    }
    fun getAppStateInteractor(sharedPreferences:SharedPreferences):AppStateInteractor{
        return AppStateInteractorImpl(sharedPreferences)
    }

    fun getPlayerRepository(): PlayerRepository {
        return PlayerRepositoryImpl()
    }
    fun getPlayerInteractor():MediaPlayerInteractor{
        return MediaPlayerInteractorImpl(getPlayerRepository())
    }

    fun getSharedPreferencesRepository(application: Application):SharedPreferencesRepository{
        return SharedPreferencesRepositoryImpl(application)
    }
    fun getNetworkClient():NetworkClient{
        return RetrofitNetworkClient()
    }
    fun getSaveListRepository():SaveListRepository{
        return SaveListRepositoryImpl()
    }
    fun getSearchInteractor(application: Application):SearchInteractor{
        return SearchInteractorImpl(getSaveListRepository(), getNetworkClient(),
            getSharedPreferencesRepository(application)
        )
    }
}
