package com.example.playlistmaker.search.domain.model

import com.example.playlistmaker.player.domain.MediaPlayerInteractor
import com.example.playlistmaker.player.domain.models.PlayerData
import com.example.playlistmaker.player.domain.models.Track

class TrackPlayModel(track:Track?, playerInteractor: MediaPlayerInteractor) {
    var track: Track? = track
    //var playerState: Int = 0
    var playerInteractor: MediaPlayerInteractor = playerInteractor
}
