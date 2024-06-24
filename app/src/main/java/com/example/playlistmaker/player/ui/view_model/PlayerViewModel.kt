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
    locIntent: IntentData,
    application: Application
) : AndroidViewModel(application) {


    private var player: MediaPlayerInteractor = Creator.getPlayerInteractor()

    private val currentTrack = player.getCurrentTrack(locIntent)
    var trackAndPlayer:TrackPlayModel = TrackPlayModel(currentTrack, player)

    companion object {
        fun getViewModelFactory(locIntent: IntentData): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    PlayerViewModel(locIntent = locIntent, this[APPLICATION_KEY] as Application)
                }
            }
    }
    private val playTrack = MutableLiveData(trackAndPlayer)
    init {
        val tmpTrack:Track? = getCurrentTrack()
        if (tmpTrack != null)
            playTrack.value?.track = tmpTrack
    }
    fun getTrackPlayLiveData(): LiveData<TrackPlayModel> = playTrack

    fun getCurrentTrack(): Track? {
        return playTrack.value?.track
    }

    fun init() {
        playTrack.value?.track?.let { playTrack.value?.playerInteractor?.init(it.previewUrl) }
    }

    fun preparePlayer() {
        playTrack.value?.playerInteractor?.preparePlayer()
    }

    fun playbackControl() {
        playTrack.value?.playerInteractor?.playbackControl()
    }

    fun startPlayer() {
        playTrack.value?.playerInteractor?.startPlayer()
    }

    fun pausePlayer() {
        playTrack.value?.playerInteractor?.pausePlayer()
    }

    fun stopPlayer() {
        playTrack.value?.playerInteractor?.stopPlayer()
    }

    fun getPlayer(): PlayerData? {
        return playTrack.value?.playerInteractor?.getPlayer()
    }

    fun getStatus(): Int? {
        return player.getStatus()
    }
}
