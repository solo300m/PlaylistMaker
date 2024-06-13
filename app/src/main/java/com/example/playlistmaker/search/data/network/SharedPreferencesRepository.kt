package com.example.playlistmaker.search.data.network

import android.content.SharedPreferences
import com.example.playlistmaker.player.domain.models.Track

interface SharedPreferencesRepository {
    fun clearTrackList(trackList:MutableList<Track>, tracks:MutableList<Track>)
    fun loadList(trackList:MutableList<Track>)
    fun writeList(trackList:MutableList<Track>)
}
