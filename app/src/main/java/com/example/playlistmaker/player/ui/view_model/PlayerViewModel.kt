package com.example.playlistmaker.player.ui.view_model

import android.app.Application
import android.media.MediaPlayer
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.player.data.dto.DataService
import com.example.playlistmaker.player.data.dto.ServiceMethod
import com.example.playlistmaker.player.domain.api.MediaPlayerInterface
import com.example.playlistmaker.player.domain.impl.MediaPlayerImpl
import com.example.playlistmaker.player.domain.models.Track

class PlayerViewModel(
    track: Track,
    application: Application) : AndroidViewModel(application) {
    private val track = track
    var player: MediaPlayerInterface = MediaPlayerImpl()
    private val service: ServiceMethod = DataService()

    companion object {
        fun getViewModelFactory(track:Track):ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PlayerViewModel(track = track, this[APPLICATION_KEY] as Application)
            }
        }
    }
    fun testViewModelPlayer(){
        Toast.makeText(getApplication(), "Запущен трек с ID ${track.trackId}", Toast.LENGTH_LONG).show()
    }
    fun init(){
        player.init(track.previewUrl)
    }
    fun preparePlayer(){
        player.preparePlayer()
    }
    fun onPause(){
        player.onPause()
    }
    fun onDestroy(){
        player.onDestroy()
    }
    fun playbackControl(){
        player.playbackControl()
    }
    fun startPlayer(){
        player.startPlayer()
    }
    fun pausePlayer(){
        player.pausePlayer()
    }
    fun getPlayer(): MediaPlayer{
        return player.getPlayer()
    }
    fun getStatus():Int{
        return player.getStatus()
    }
}
