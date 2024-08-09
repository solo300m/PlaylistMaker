package com.example.playlistmaker.search.domain.model

import com.example.playlistmaker.player.domain.MediaPlayerInteractor
import com.example.playlistmaker.player.domain.models.PlayerData
import com.example.playlistmaker.player.domain.models.Track

class TrackPlayModel(track:Track?, playerState: Int?) {
    var track: Track? = track
    var playerState: Int? = playerState
    //var playerInteractor: MediaPlayerInteractor = playerInteractor
}
