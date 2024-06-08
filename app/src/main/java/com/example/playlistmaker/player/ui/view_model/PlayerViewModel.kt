package com.example.playlistmaker.player.ui.view_model

import android.app.Application
import android.content.Intent
import android.media.MediaPlayer
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.player.ui.utils.DataService
import com.example.playlistmaker.player.ui.utils.ServiceMethod
import com.example.playlistmaker.player.domain.api.MediaPlayerInteractor
import com.example.playlistmaker.player.domain.impl.MediaPlayerInteractorImpl
import com.example.playlistmaker.player.data.impl.PlayerRepositoryImpl
import com.example.playlistmaker.player.domain.models.Track

class PlayerViewModel(
    intent: Intent,
    application: Application
) : AndroidViewModel(application) {


    var player: MediaPlayerInteractor = MediaPlayerInteractorImpl(intent, PlayerRepositoryImpl(intent))
    private val service: ServiceMethod = DataService()
    private val currentTrack = player.getCurrentTrack()

    //private val track = currentTrack
    companion object {
        fun getViewModelFactory(intent: Intent): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PlayerViewModel(intent = intent, this[APPLICATION_KEY] as Application)
            }
        }
    }

    fun getCurrentTrack(): Track {
        return currentTrack
    }

    fun testViewModelPlayer() {
        Toast.makeText(
            getApplication(),
            "Запущен трек с ID ${currentTrack.trackId}",
            Toast.LENGTH_LONG
        ).show()
    }

    fun init() {
        player.init(currentTrack.previewUrl)
    }

    fun preparePlayer() {
        player.preparePlayer()
    }

    /*fun onPause(){
        player.onPause()
    }
    fun onDestroy(){
        player.onDestroy()
    }*/
    fun playbackControl() {
        player.playbackControl()
    }

    fun startPlayer() {
        player.startPlayer()
    }

    fun pausePlayer() {
        player.pausePlayer()
    }
    fun stopPlayer(){
        player.stopPlayer()
    }
    fun getPlayer(): MediaPlayer {
        return player.getPlayer()
    }

    fun getStatus(): Int {
        return player.getStatus()
    }
}
