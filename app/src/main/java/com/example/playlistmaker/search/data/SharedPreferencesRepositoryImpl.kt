package com.example.playlistmaker.search.data

import android.content.Context
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.player.domain.models.Track

import com.google.gson.Gson

class SharedPreferencesRepositoryImpl (context: Context): SharedPreferencesRepository {
    private val sharedRep = Creator.getSharedRepository()
    private val sharedPref = sharedRep.getSharedPreferences(context, TRACK_LIST_KEY)

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
        val json = sharedPref.sharedPreferences?.getString(TRACK_LIST_KEY, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<Track>::class.java)
    }
    private fun write(
        tracksList: List<Track>
    ) {
        val json = Gson().toJson(tracksList)
        sharedPref.sharedPreferences?.edit()
            ?.putString(TRACK_LIST_KEY, json)
            ?.apply()
    }

}
