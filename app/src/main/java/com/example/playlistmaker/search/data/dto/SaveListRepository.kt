package com.example.playlistmaker.search.data.dto

import com.example.playlistmaker.player.domain.models.Track

interface SaveListRepository {
    fun getTracksTmp():ArrayList<Track>
    fun addTrackToList(trackList:MutableList<Track>, unit: Track)
    fun onFindToTrack(tracks:MutableList<Track>, input: Long)
    fun getTrack(tracks:MutableList<Track>, input: Long): Track?
}
