package com.example.playlistmaker.search.data.network

import android.app.Activity
import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.playlistmaker.player.domain.models.Track
import com.example.playlistmaker.search.data.dto.TRACK_LIST_KEY
import com.example.playlistmaker.search.ui.activity.FindActivity
import com.google.gson.Gson

class SharedPreferencesRepositoryImpl (application: Application): SharedPreferencesRepository {

    private val sharedPreferences = application.getSharedPreferences(TRACK_LIST_KEY, MODE_PRIVATE)
    override fun loadList() {
        val tmpArray = read();
        FindActivity.trackList.clear()
        FindActivity.trackList.addAll(tmpArray)
    }

    override fun writeList() {
        write(FindActivity.trackList)
    }

    override fun clearTrackList() {
        FindActivity.trackList.clear()
        FindActivity.tracks.clear()
        writeList()
    }
    private fun read(): Array<Track> {
        val json = sharedPreferences.getString(TRACK_LIST_KEY, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<Track>::class.java)
    }
    private fun write(
        tracksList: List<Track>
    ) {
        val json = Gson().toJson(tracksList)
        sharedPreferences.edit()
            .putString(TRACK_LIST_KEY, json)
            .apply()
    }

}
