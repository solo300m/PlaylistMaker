package com.example.playlistmaker.creator

import android.app.Application
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import com.example.playlistmaker.main.domain.SharedRepository
import com.example.playlistmaker.main.data.SharedRepositoryImpl
import com.example.playlistmaker.main.domain.api.AppStateInteractor
import com.example.playlistmaker.main.domain.impl.AppStateInteractorImpl
import com.example.playlistmaker.player.domain.models.PlayerData
import com.example.playlistmaker.player.domain.api.PlayerRepository
import com.example.playlistmaker.player.domain.impl.PlayerRepositoryImpl
import com.example.playlistmaker.player.domain.MediaPlayerInteractor
import com.example.playlistmaker.player.domain.MediaPlayerInteractorImpl
import com.example.playlistmaker.player.domain.models.Track
import com.example.playlistmaker.search.data.network.NetworkClient
import com.example.playlistmaker.search.data.SaveListRepository
import com.example.playlistmaker.search.data.SaveListRepositoryImpl
import com.example.playlistmaker.search.domain.SearchInteractor
import com.example.playlistmaker.search.data.SharedPreferencesRepository
import com.example.playlistmaker.search.data.network.RetrofitNetworkClient
import com.example.playlistmaker.search.domain.SearchInteractorImpl
import com.example.playlistmaker.search.data.SharedPreferencesRepositoryImpl


const val SETTING_SAVE: String = "set theme"
//val application = APPLICATION_KEY

object Creator {

    fun getMediaPlayer(): PlayerData {
        return PlayerData(MediaPlayer(), 0)
    }


    fun getSharedRepository(): SharedRepository {
        return SharedRepositoryImpl()
    }

    fun getAppStateInteractor(context: Context): AppStateInteractor {
        return AppStateInteractorImpl(
            getSharedRepository().getSharedPreferences(
                context,
                SETTING_SAVE
            )
        )
    }

    fun getTrackByIntent(intent: Intent): Track {
        val track = Track(
            trackId = intent.getLongExtra("trackId", 0L),
            trackName = intent.getStringExtra("trackName").toString(),
            artworkUrl100 = intent.getStringExtra("trackPicture").toString(),
            artistName = intent.getStringExtra("nameSinger").toString(),
            trackTimeMillis = intent.getLongExtra("longTime", 0L),
            collectionName = intent.getStringExtra("album").toString(),
            country = intent.getStringExtra("country").toString(),
            releaseDate = intent.getStringExtra("realiseDate").toString(),
            primaryGenreName = intent.getStringExtra("genreName").toString(),
            previewUrl = intent.getStringExtra("url").toString(),
        );
        return track
    }

    fun getPlayerRepository(): PlayerRepository {
        return PlayerRepositoryImpl()
    }

    fun getPlayerInteractor(): MediaPlayerInteractor {
        return MediaPlayerInteractorImpl(getPlayerRepository())
    }

    fun getSharedPreferencesRepository(application: Application): SharedPreferencesRepository {
        return SharedPreferencesRepositoryImpl(application)
    }

    fun getNetworkClient(): NetworkClient {
        return RetrofitNetworkClient()
    }

    fun getSaveListRepository(): SaveListRepository {
        return SaveListRepositoryImpl()
    }

    fun getSearchInteractor(application: Application): SearchInteractor {
        return SearchInteractorImpl(
            getSaveListRepository(), getNetworkClient(),
            getSharedPreferencesRepository(application)
        )
    }
}
