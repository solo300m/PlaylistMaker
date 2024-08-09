package com.example.playlistmaker.player.ui.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.player.domain.models.IntentData
import com.example.playlistmaker.player.domain.models.PlayerData
import com.example.playlistmaker.player.domain.MediaPlayerInteractor
import com.example.playlistmaker.player.domain.models.Track
import com.example.playlistmaker.search.domain.model.TrackPlayModel

class PlayerViewModel(
    track: Track,
    application: Application
) : AndroidViewModel(application) {


    //private var player: MediaPlayerInteractor = Creator.getPlayerInteractor()

    private val currentTrack = track
    private var trackAndPlayer: TrackPlayModel =
        TrackPlayModel(currentTrack, playerState = 0, Creator.getPlayerInteractor())

    companion object {
        fun getViewModelFactory(track: Track): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    PlayerViewModel(track = track, this[APPLICATION_KEY] as Application)
                }
            }
    }

    private val playTrack = MutableLiveData(trackAndPlayer)

    /*init {
        val tmpTrack: Track? = getCurrentTrack()
        if (tmpTrack != null)
            playTrack.value?.track = tmpTrack
        playTrack.value?.playerState = 0
    }*/

    fun getTrackPlayLiveData(): LiveData<TrackPlayModel> = playTrack

    fun getCurrentTrack(): Track? {
        return getTrackPlayLiveData().value?.track
    }

    fun init() {
        playTrack.value?.playerInteractor?.init(expression=playTrack.value?.track?.previewUrl.toString())
    }

    fun preparePlayer() {
        playTrack.value?.playerInteractor?.preparePlayer()
        playTrack.value?.playerState = 1
    }

    fun playbackControl() {
        playTrack.value?.playerInteractor?.playbackControl()
    }

    fun startPlayer() {
        playTrack.value?.playerInteractor?.startPlayer()
        playTrack.value?.playerState = 2
    }

    fun pausePlayer() {
        playTrack.value?.playerInteractor?.pausePlayer()
        playTrack.value?.playerState = 3
    }

    fun stopPlayer() {
        playTrack.value?.playerInteractor?.stopPlayer()
        playTrack.value?.playerState = 0
    }

    fun getPlayer(): PlayerData? {
        return getTrackPlayLiveData().value?.playerInteractor?.getPlayer()
    }

    fun getStatus(): Int? {
        return getTrackPlayLiveData().value?.playerInteractor?.getStatus()
    }
}
