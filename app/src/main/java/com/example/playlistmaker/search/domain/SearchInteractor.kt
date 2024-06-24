package com.example.playlistmaker.search.domain

import com.example.playlistmaker.player.domain.models.Track
import com.example.playlistmaker.search.domain.model.ITunes
import com.example.playlistmaker.search.domain.model.Response

interface SearchInteractor {
    fun getTracksTmp():ArrayList<Track>
    fun addTrackToList(trackList:MutableList<Track>, unit: Track)
    fun onFindToTrack(tracks:MutableList<Track>, input: Long)
    fun getTrack(tracks:MutableList<Track>, input: Long): Track?

    fun doRequest(dto:String): Response
    fun getITunesClient(): ITunes

    fun clearTrackList(trackList:MutableList<Track>, tracks:MutableList<Track>)
    fun loadList(trackList:MutableList<Track>)
    fun writeList(trackList:MutableList<Track>)
}
