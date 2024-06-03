package com.example.playlistmaker.search.data.dto

import android.content.SharedPreferences

interface SharedPreferencesInterface {
    fun clearTrackList(sharedPreferences: SharedPreferences)
    fun loadList(sharedPreferences: SharedPreferences)
    fun writeList(sharedPreferences: SharedPreferences)
}
