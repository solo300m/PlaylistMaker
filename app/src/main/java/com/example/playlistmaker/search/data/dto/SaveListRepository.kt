package com.example.playlistmaker.search.data.dto

import com.example.playlistmaker.player.domain.models.Track

interface SaveListRepository {
    fun getTracksTmp():ArrayList<Track>
    fun addTrackToList(unit: Track)
    fun onFindToTrack(input: Long)
    fun getTrack(input: Long): Track?
}
