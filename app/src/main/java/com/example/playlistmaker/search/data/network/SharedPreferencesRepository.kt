package com.example.playlistmaker.search.data.network

import android.content.SharedPreferences

interface SharedPreferencesRepository {
    fun clearTrackList()
    fun loadList()
    fun writeList()
}
