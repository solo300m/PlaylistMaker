package com.example.playlistmaker.search.domain.model

import com.example.playlistmaker.player.domain.models.Track

class TracksListModel {
    var trackList: MutableList<Track> = mutableListOf()
    var statusTracksList: Boolean = false
}
