package com.example.playlistmaker.search.data.dto

import android.content.SharedPreferences
import com.example.playlistmaker.player.domain.models.Track
import com.example.playlistmaker.search.ui.activity.FindActivity
import com.google.gson.Gson

class SharedPreferencesImpl : SharedPreferencesInterface {

    override fun loadList(sharedPreferences: SharedPreferences) {
        val tmpArray = read(sharedPreferences);
        FindActivity.trackList.clear()
        FindActivity.trackList.addAll(tmpArray)
    }

    override fun writeList(sharedPreferences: SharedPreferences) {
        write(sharedPreferences, FindActivity.trackList)
    }

    override fun clearTrackList(sharedPreferences: SharedPreferences) {
        FindActivity.trackList.clear()
        FindActivity.tracks.clear()
        writeList(sharedPreferences)
    }
    private fun read(sharedPreferences: SharedPreferences): Array<Track> {
        val json = sharedPreferences.getString(TRACK_LIST_KEY, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<Track>::class.java)
    }
    private fun write(
        sharedPreferences: SharedPreferences,
        tracksList: List<Track>
    ) {
        val json = Gson().toJson(tracksList)
        sharedPreferences.edit()
            .putString(TRACK_LIST_KEY, json)
            .apply()
    }

}
