package com.example.playlistmaker.find

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.view.View
import android.widget.Toast
import com.example.playlistmaker.R
import com.example.playlistmaker.find.FindActivity.Companion.trackList
import com.example.playlistmaker.find.FindActivity.Companion.tracks
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

const val TRACK_LIST_KEY: String = "list of 10 signs"

class TrackPreferences {
    //var trackList: MutableList<Track> = mutableListOf()
    val trackTmp = ArrayList<Track>()

    fun addTrackToList(unit: Track) {
        var idx = -1
        if (unit != null) {
            for (i in 0..trackList.size-1) {
                if (trackList[i] == unit) {
                    idx = i
                    break
                }
            }
            if(idx != -1){
                trackList.removeAt(idx)
            }
            if (trackList.size < 10) {
                trackList.add(unit)
            } else {
                trackList.removeAt(0)
                trackList.add(unit)
            }
        }
    }

    fun onFindToTrack(input: Long) {
        if (input != 0L && !tracks.isEmpty()) {
            for(i in 0..tracks.size-1){
                if(tracks[i].trackId == input){
                    trackTmp.add(tracks[i])
                }
            }
        }
    }

}
