package com.example.playlistmaker.search.domain

import android.content.SharedPreferences
import com.example.playlistmaker.player.domain.models.Track
import com.example.playlistmaker.search.data.dto.ITunes
import com.example.playlistmaker.search.data.dto.Response

interface SearchInteractor {
    fun getTracksTmp():ArrayList<Track>
    fun addTrackToList(unit: Track)
    fun onFindToTrack(input: Long)
    fun getTrack(input: Long): Track?

    fun doRequest(dto:String): Response
    fun getITunesClient(): ITunes

    fun clearTrackList()
    fun loadList()
    fun writeList()
}
