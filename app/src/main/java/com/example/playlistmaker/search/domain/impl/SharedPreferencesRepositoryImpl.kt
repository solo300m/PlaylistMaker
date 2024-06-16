package com.example.playlistmaker.search.domain.impl

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.example.playlistmaker.player.domain.models.Track
import com.example.playlistmaker.search.domain.api.SharedPreferencesRepository
import com.google.gson.Gson

class SharedPreferencesRepositoryImpl (application: Application): SharedPreferencesRepository {


    private val sharedPreferences = application.getSharedPreferences(TRACK_LIST_KEY, MODE_PRIVATE)

    override fun loadList(trackList:MutableList<Track>) {
        val tmpArray = read();
        trackList.clear()
        trackList.addAll(tmpArray)
    }

    override fun writeList(trackList:MutableList<Track>) {
        write(trackList)
    }

    override fun clearTrackList(trackList:MutableList<Track>, tracks:MutableList<Track>) {
        trackList.clear()
        tracks.clear()
        writeList(trackList)
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
